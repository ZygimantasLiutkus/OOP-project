package server.api;

import commons.GameEntity;
import commons.Player;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.GameEntityRepository;
import server.database.PlayerRepository;
import server.services.QuestionService;

/**
 * Main Controller for the game.
 */
@RestController
@RequestMapping("api/game")
public class GameEntityController {
  private final GameEntityRepository repo;
  private final PlayerRepository playerRepo;
  private final QuestionService service;

  /**
   * Constructor for the controller.
   *
   * @param repo the game repository
   * @param playerRepo the player repository
   * @param service the service for questions
   */
  public GameEntityController(GameEntityRepository repo, PlayerRepository playerRepo,
                              QuestionService service) {
    this.repo = repo;
    this.playerRepo = playerRepo;
    this.service = service;
  }

  /**
   * GET request that responds with all games.
   *
   * @return a list of all games
   */
  @GetMapping(path = {"", "/"})
  public ResponseEntity<List<GameEntity>> getAllGames() {
    return ResponseEntity.ok(repo.findAll());
  }

  /**
   * A GET request that finds a game based on its ID and returns it.
   *
   * @param id the ID of the game
   * @return the ResponseEntity of the game that was requested
   */
  @GetMapping(path = "/{id}")
  public ResponseEntity<GameEntity> getGameById(@PathVariable("id") long id) {
    return ResponseEntity.of(repo.findById(id));
  }

  /**
   * Returns if the supplied string is null or empty.
   *
   * @param s the string to check
   * @return true iff the string is null or empty
   */
  @SuppressWarnings("unused")
  private static boolean isNullOrEmpty(String s) {
    return s == null || s.isEmpty();
  }

  /**
   * GET method that returns all games with the specified status.
   *
   * @param status the wanted status
   * @return a list of all the games with said status
   */
  @GetMapping(path = "?status={status}")
  public List<GameEntity> getGameByStatus(@PathVariable("status") String status) {
    List<GameEntity> response = new ArrayList<>();
    for (GameEntity ge : repo.findAll()) {
      if (ge.getStatus().equals(status)) {
        response.add(ge);
      }
    }
    return response;
  }

  /**
   * GET method that returns the status of the game with a specific ID.
   *
   * @param id the ID of the game
   * @return a ResponseEntity of the requested status.
   */
  @GetMapping(path = "/{id}/status")
  public ResponseEntity<String> getGameStatusById(@PathVariable("id") Long id) {
    if (repo.findById(id).isEmpty()) {
      return ResponseEntity.badRequest().build();
    } else {
      return ResponseEntity.ok(repo.findById(id).get().getStatus());
    }
  }

  /**
   * PUT method that changes the status of a game.
   * Returns an error if the new status is earlier in the chain of possible statuses.
   *
   * @param id the id of the game
   * @param newStatus the wanted status for the game
   * @return ResponseEntity of the game
   */
  @PutMapping(path = "/{id}/status")
  public ResponseEntity<GameEntity> changeGameStatus(@PathVariable("id") long id,
                                                 @RequestBody String newStatus) {
    if (repo.findById(id).isEmpty()) {
      return ResponseEntity.badRequest().build();
    } else {
      GameEntity ge = repo.findById(id).get();
      if ((ge.getStatus().equals("ABORTED") && !newStatus.equals("ABORTED"))
              || (ge.getStatus().equals("FINISHED") && (newStatus.equals("WAITING")
              || newStatus.equals("STARTED"))) || (ge.getStatus().equals("STARTED")
              && newStatus.equals("WAITING"))) {
        return ResponseEntity.badRequest().build();
      }
      ge.setStatus(newStatus);
      repo.deleteById(id);
      repo.save(ge);
      return ResponseEntity.ok(repo.findById(id).get());
    }
  }

  /**
   * GET method that returns a list of all players engaged in a game
   * with a specific id. If a game with this id does not exist, returns
   * an error.
   *
   * @param id the id of the game
   * @return ResponseEntity of the list of players
   */
  @GetMapping(path = "/{id}/player")
  public ResponseEntity<List<Player>> getAllPlayers(@PathVariable("id") long id) {
    if (repo.findById(id).isEmpty()) {
      return ResponseEntity.badRequest().build();
    } else {
      return ResponseEntity.ok(repo.findById(id).get().getPlayers());
    }
  }

  /**
   * POST method that adds a new player to a game.
   * Returns an error if the game does not exist or a player with
   * the same name already exists in the game.
   *
   * @param id the id of the game
   * @param playerName the name of the new player
   * @return ResponseEntity of the new player
   */
  @PostMapping(path = "/{id}/player")
  public ResponseEntity<Player> addPlayerToGame(@PathVariable("id") long id, String playerName) {
    if (repo.findById(id).isEmpty()) {
      return ResponseEntity.badRequest().build();
    } else {
      if (!repo.findById(id).get().getStatus().equals("WAITING")) {
        return ResponseEntity.badRequest().build();
      }
      for (Player p : repo.findById(id).get().getPlayers()) {
        if (p.getName().equals(playerName)) {
          return ResponseEntity.unprocessableEntity().build();
        }
      }
      Player newPlayer = new Player(playerName);
      playerRepo.save(newPlayer);
      GameEntity ge = repo.findById(id).get();
      List<Player> playersList = ge.getPlayers();
      playersList.add(newPlayer);
      ge.setPlayers(playersList);
      repo.deleteById(id);
      repo.save(ge);
      return ResponseEntity.ok(newPlayer);
    }
  }
}

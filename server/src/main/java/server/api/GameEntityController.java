package server.api;

import commons.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.GameEntityRepository;
import server.database.PlayerRepository;
import server.database.QuestionRepository;
import server.services.LeaderboardService;
import server.services.QuestionService;

/**
 * Main Controller for the game.
 */
@RestController
@RequestMapping("api/game")
public class GameEntityController {
  private final GameEntityRepository repo;
  private final PlayerRepository playerRepo;
  private final QuestionService questionService;
  private final QuestionRepository qRepo;
  private final LeaderboardService leaderboardService;

  /**
   * Constructor for the controller.
   *
   * @param repo               the game repository
   * @param playerRepo         the player repository
   * @param questionService    the service for questions
   * @param qRepo              the question repository
   * @param leaderboardService the leaderboard service
   */
  public GameEntityController(GameEntityRepository repo, PlayerRepository playerRepo,
                              QuestionService questionService, QuestionRepository qRepo,
                              LeaderboardService leaderboardService) {
    this.repo = repo;
    this.playerRepo = playerRepo;
    this.questionService = questionService;
    this.qRepo = qRepo;
    this.leaderboardService = leaderboardService;
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
    for (long i = 0; i <= repo.findAll().size(); i++) {
      if (i == id) {
        return ResponseEntity.ok(repo.getById(id));
      }
    }
    return ResponseEntity.badRequest().build();

  }

  /**
   * GET method that returns all games with the specified status.
   *
   * @param status the wanted status
   * @return a list of all the games with said status
   */
  @GetMapping(path = "?status={status}")
  public ResponseEntity<List<GameEntity>> getGameByStatus(@PathVariable("status") String status) {
    List<GameEntity> response = new ArrayList<>();
    for (GameEntity ge : repo.findAll()) {
      if (ge.getStatus().equals(status)) {
        response.add(ge);
      }
    }
    return ResponseEntity.ok(response);
  }

  /**
   * GET method that returns the status of the game with a specific ID.
   *
   * @param id the ID of the game
   * @return a ResponseEntity of the requested status.
   */
  @GetMapping(path = "/{id}/status")
  public ResponseEntity<String> getGameStatusById(@PathVariable("id") long id) {
    if (repo.existsById(id)) {
      return ResponseEntity.ok(repo.getById(id).getStatus());
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * PUT method that changes the status of a game.
   * Returns an error if the new status is earlier in the chain of possible statuses.
   *
   * @param id        the id of the game
   * @param newStatus the game with the wanted status
   * @return ResponseEntity of the game with the edited status
   */
  @PutMapping(path = "/{id}")
  public ResponseEntity<GameEntity> changeGameStatus(@PathVariable("id") long id,
                                                     @RequestBody GameEntity newStatus) {
    if (!repo.existsById(id)) {
      return ResponseEntity.badRequest().build();
    } else {
      GameEntity ge = repo.getById(id);
      if ((ge.getStatus().equals("ABORTED") && !newStatus.getStatus().equals("ABORTED"))
          || (ge.getStatus().equals("FINISHED") && (newStatus.getStatus().equals("WAITING")
          || newStatus.getStatus().equals("STARTED"))) || (ge.getStatus().equals("STARTED")
          && newStatus.getStatus().equals("WAITING"))) {
        return ResponseEntity.badRequest().build();
      }
      ge.setStatus(newStatus.getStatus());
      return ResponseEntity.ok(repo.save(ge));
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
  public ResponseEntity<List<Player>> getAllPlayers(@PathVariable("id") Long id) {
    if (repo.existsById(id)) {
      return ResponseEntity.ok(repo.getById(id).getPlayers());
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * POST method that adds a new player to a game with waiting status.
   * Returns an error if a player with the same name already exists in the game.
   * If there is no game with waiting status, one is created and the player is
   * added.
   *
   * @param player the player that has to be added
   * @return ResponseEntity of the game in which the player was added
   */
  @PostMapping(path = "/addPlayer")
  public ResponseEntity<GameEntity> addPlayerToGame(@RequestBody Player player) {
    final int questionAmount = 20;
    List<GameEntity> status = repo.findByStatus("WAITING");
    List<GameEntity> type = repo.findByType(GameEntity.Type.MULTIPLAYER);
    List<GameEntity> list = status.stream().filter(type::contains).collect(Collectors.toList());
    if (list.size() == 0) { // Create a new game
      GameEntity game = new GameEntity();
      List<Question> questions = questionService.generateQuestion(questionAmount);
      if (questions.size() != questionAmount) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
      game.setType(GameEntity.Type.MULTIPLAYER);
      game.setQuestions(questions);
      repo.save(game);
      player.setGameId(game.getId());
      playerRepo.save(player);
      game.addPlayer(player);
      return ResponseEntity.ok(repo.save(game));
    }
    // Get the first multiplayer game with the status WAITING
    GameEntity game = list.get(0);
    // Check if the provided name is already in use in this game
    for (Player p : game.getPlayers()) {
      if (p.getName().equals(player.getName())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
    }
    // Save the player and add it to the game
    player.setGameId(game.getId());
    playerRepo.save(player);
    game.addPlayer(player);
    return ResponseEntity.ok(repo.save(game));
  }

  /**
   * GET endpoint for retrieving the list of questions.
   *
   * @param id the game id
   * @return a list of all 20 questions (supposedly)
   */
  @GetMapping(path = "/{id}/question")
  public ResponseEntity<List<Question>> getAllQuestions(@PathVariable("id") long id) {
    if (repo.existsById(id)) {
      return ResponseEntity.ok(repo.getById(id).getQuestions());
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * POST request to map a player with an answer.
   * Checks if the player is actually present in the game.
   * Checks if the game has started.
   * For "What's more expensive" the answer is the biggest consumption.
   * For the Multiple Choice question the logic is a placeholder.
   * For the Estimation question the logic is a placeholder.
   * The player's score will be updated.
   * (For now un "ugly" version of checking an answer)
   *
   * @param id     the game's id
   * @param idq    the question number
   * @param player the player that has answered
   * @return an answer containing feedback about the submission
   */
  @PostMapping(path = "/{id}/question/{idQ}")
  public ResponseEntity<Answer> answer(@PathVariable("id") long id, @PathVariable("idQ") long idq,
                                       @RequestBody Player player) {
    boolean found = false;
    Player playerDummy = new Player();
    for (GameEntity game : repo.findAll()) {
      if (game.getId() == id) {
        if (!game.getStatus().equals("STARTED")) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        for (Player p : game.getPlayers()) {
          if (Objects.equals(p.getId(), player.getId())) {
            playerDummy = p;
            found = true;
            break;
          }
        }
        if (!found) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Question q = game.getQuestions().get((int) idq - 1);
        if (q instanceof QuestionMoreExpensive) {
          int maxim = 0;
          for (Activity a : q.getActivities()) {
            if (a.getConsumption_in_wh() > maxim) {
              maxim = a.getConsumption_in_wh();
            }
          }
          if (Integer.parseInt(player.getSelectedAnswer()) == maxim) {
            playerDummy.setScore(playerDummy.getScore() + 100);
            playerRepo.save(playerDummy);
            return ResponseEntity.ok(
                new Answer("CORRECT", playerDummy, playerDummy.getScore(), 100));
          } else {
            playerDummy.setScore(playerDummy.getScore());
            playerRepo.save(playerDummy);
            return ResponseEntity.ok(
                new Answer("INCORRECT", playerDummy, playerDummy.getScore(), 0));
          }
        } else if (q instanceof QuestionMultipleChoice) {
          if (Integer.parseInt(player.getSelectedAnswer())
              == q.getActivities().get(Integer.parseInt(player.getSelectedAnswer()) - 1)
              .getConsumption_in_wh()) {
            playerDummy.setScore(playerDummy.getScore() + 100);
            playerRepo.save(playerDummy);
            return ResponseEntity.ok(
                new Answer("CORRECT", playerDummy, playerDummy.getScore(), 100));
          } else {
            playerDummy.setScore(playerDummy.getScore());
            playerRepo.save(playerDummy);
            return ResponseEntity.ok(
                new Answer("INCORRECT", playerDummy, playerDummy.getScore(), 0));
          }
        } else {
          if (Integer.parseInt(player.getSelectedAnswer())
              == q.getActivities().get(0).getConsumption_in_wh()) {
            playerDummy.setScore(playerDummy.getScore() + 100);
            playerRepo.save(playerDummy);
            return ResponseEntity.ok(
                new Answer("CORRECT", playerDummy, playerDummy.getScore(), 100));
          } else {
            playerDummy.setScore(playerDummy.getScore());
            playerRepo.save(playerDummy);
            return ResponseEntity.ok(
                new Answer("INCORRECT", playerDummy, playerDummy.getScore(), 0));
          }
        }
      }
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * GET request for a specific question.
   *
   * @param id the game's id
   * @param q  the question number (1 - 20)
   * @return the content of the question
   */
  @GetMapping(path = "/{id}/question/{idQ}")
  public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long id,
                                                  @PathVariable("idQ") int q) {
    if (!repo.existsById(id) || q <= 0 || q > 20) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(repo.getById(id).getQuestions().get(q - 1));
  }

  /**
   * GET request for the leaderboard of a multiplayer game.
   *
   * @param id the id of the game
   * @return the generated leaderboard
   */
  @GetMapping(path = "/{id}/leaderboard")
  public ResponseEntity<List<LeaderboardEntry>> getLeaderboard(@PathVariable("id") Long id) {
    if (!repo.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    GameEntity game = repo.getById(id);
    if (game.getType() != GameEntity.Type.MULTIPLAYER) {
      return ResponseEntity.badRequest().build();
    }
    List<LeaderboardEntry> leaderboard = leaderboardService.generateLeaderboard(game);
    return ResponseEntity.ok(leaderboard);
  }

  /**
   * POST request to create a single player game.
   *
   * @param player the player that has requested
   * @return the newly created game
   */
  @PostMapping(path = "/singleplayer")
  public ResponseEntity<GameEntity> addSingleplayer(@RequestBody Player player) {
    GameEntity game = repo.save(new GameEntity());
    List<Question> questions = qRepo.saveAll(questionService.generateQuestion(20));
    game.setType(GameEntity.Type.SINGLEPLAYER);
    game.setQuestions(questions);
    player.setGameId(game.getId());
    playerRepo.save(player);
    game.addPlayer(player);
    return ResponseEntity.ok(repo.save(game));
  }

}

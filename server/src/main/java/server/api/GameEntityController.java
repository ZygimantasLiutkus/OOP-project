package server.api;

import commons.GameEntity;
import commons.LeaderboardEntry;
import commons.Message;
import commons.Player;
import commons.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.GameEntityRepository;
import server.database.PlayerRepository;
import server.database.QuestionRepository;
import server.services.AnswerService;
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
  private final AnswerService answerService;

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
    this.answerService = new AnswerService(repo, playerRepo);
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
    Optional<GameEntity> optional = repo.findById(id);
    return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
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
   * Sends the variables to the answer service to be processed.
   * Applies different score calculations for different types of questions.
   * The player's score will be updated.
   *
   * @param id     the game's id
   * @param player the player that has answered
   * @return responseEntity
   */
  @PostMapping(path = "/{id}/scores")
  public ResponseEntity answer(@PathVariable("id") long id, @RequestBody Player player) {
    return answerService.updateScore(id, player);
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

  /**
   * Method that takes a message from /app/messages and returns it to /topic/messages.
   *
   * @param id      the id of the game
   * @param message the message being sent
   * @return the same message
   */
  @MessageMapping("/messages/{id}") // is /app/messages
  @SendTo("/topic/messages/{id}")
  public Message addMessageToGameByID(@Payload Message message, @DestinationVariable Long id) {
    return message;
  }

  /**
   * Method to update list of players.
   *
   * @param id      the id of the game.
   * @param players the list of players
   * @return the updated game
   */
  @PutMapping(path = "/{id}/updatePlayer")
  public ResponseEntity<GameEntity> updatePlayers(@PathVariable Long id,
                                                  @RequestBody List<Player> players) {
    if (!repo.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    GameEntity game = repo.getById(id);
    game.setPlayers(players);
    return ResponseEntity.ok(repo.save(game));
  }

}

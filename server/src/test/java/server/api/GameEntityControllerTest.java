package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

import commons.Activity;
import commons.GameEntity;
import commons.LeaderboardEntry;
import commons.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.QuestionRepository;
import server.database.TestActivityRepository;
import server.database.TestGameRepository;
import server.database.TestPlayerRepository;
import server.database.TestQuestionRepository;
import server.services.LeaderboardService;
import server.services.QuestionService;

/**
 * Tests for the GameEntity Controller.
 */
public class GameEntityControllerTest {

  private GameEntityController sut;
  private TestGameRepository repo;
  private QuestionService qService;
  private TestPlayerRepository playerRepo;
  private TestActivityRepository activityRepo;
  private Random random;
  private QuestionRepository qRepo;
  private LeaderboardService lService;

  /**
   * Returns a new player.
   *
   * @param s the name of the player
   * @return the new player
   */
  private static Player getPlayer(String s) {
    return new Player(s);
  }

  /**
   * Returns a new activity.
   *
   * @param id                the activity's id
   * @param title             the title
   * @param consumption_in_wh the consumption
   * @param path              the path of the activity
   * @return the new activity
   */
  private static Activity getActivity(String id, String title, int consumption_in_wh, String path) {
    return new Activity(id, title, consumption_in_wh, path);
  }

  /**
   * Initializes GameEntityControllerTest.
   */
  @BeforeEach
  public void setUp() {
    repo = new TestGameRepository();
    playerRepo = new TestPlayerRepository();
    activityRepo = new TestActivityRepository();
    for (int i = 0; i < 60; ++i) {
      activityRepo.save(getActivity(Integer.toString(i), "activity5", 17, "path3"));
    }
    random = new Random();
    qService = new QuestionService(activityRepo, random);
    qRepo = new TestQuestionRepository();
    lService = new LeaderboardService();
    sut = new GameEntityController(repo, playerRepo, qService, qRepo, lService);
  }

  /**
   * Tests that you can't have 2 players with the same name.
   */
  @Test
  public void noPlayersWithSameName() {
    sut.addPlayerToGame(getPlayer("Bob"));
    var actual = sut.addPlayerToGame(getPlayer("Bob"));
    assertEquals(CONFLICT, actual.getStatusCode());
  }

  /**
   * Tests that the player database and the game database are used when adding a player.
   */
  @Test
  public void databasesAreUsed() {
    sut.addPlayerToGame(getPlayer("Alice"));
    assertTrue(repo.used.contains("save"));
    assertTrue(playerRepo.used.contains("save"));
  }

  /**
   * Tests if a new player is added to a game with status 'WAITING' if one already
   * exists, instead of creating a new game.
   */
  @Test
  public void testAddToWaitingGame() {
    Player alice = getPlayer("Alice");
    Player bob = getPlayer("Bob");
    sut.addPlayerToGame(alice);
    sut.addPlayerToGame(bob);
    GameEntity game = repo.findAll().get(0);
    assertEquals(game.getPlayers().get(0), alice);
    assertEquals(game.getPlayers().get(1), bob);
  }

  /**
   * Test if getGameById() retrieves the correct game.
   */
  @Test
  public void testGetGameByID() {
    repo.deleteAll();
    GameEntity game = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    long id = game.getId();
    assertEquals(game, sut.getGameById(id).getBody());
  }

  /**
   * Test if getGameById() returns an error for an id that doesn't exist.
   */
  @Test
  public void testGetGameByIDNonexistent() {
    GameEntity game = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    Long id = game.getId() + 2;
    assertEquals(BAD_REQUEST, sut.getGameById(id).getStatusCode());
  }

  /**
   * Test for getting the status of an existing game.
   */
  @Test
  public void testGetGameStatusById() {
    GameEntity game = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    Long id = game.getId();
    assertEquals("WAITING", sut.getGameStatusById(id).getBody());
  }

  /**
   * Test for getting the status of a non-existing game.
   */
  @Test
  public void testGetGameStatusNonEx() {
    GameEntity game = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    Long id = game.getId() + 1;
    assertEquals(BAD_REQUEST, sut.getGameStatusById(id).getStatusCode());
  }

  /**
   * Test for getting a list of all the players in a game.
   */
  @Test
  public void testGetAllPlayers() {
    Player alice = getPlayer("Alice");
    Player bob = getPlayer("Bob");
    sut.addPlayerToGame(alice);
    sut.addPlayerToGame(bob);
    GameEntity game = repo.findAll().get(0);
    Long id = game.getId();
    assertEquals(alice, sut.getAllPlayers(id).getBody().get(0));
    assertEquals(bob, sut.getAllPlayers(id).getBody().get(1));
  }

  /**
   * Test for getting all the players of a non-existent game.
   */
  @Test
  public void testGetAllPlayersBadId() {
    sut.addPlayerToGame(getPlayer("Alice"));
    GameEntity game = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    assertEquals(BAD_REQUEST, sut.getAllPlayers(game.getId() + 1).getStatusCode());
  }

  /**
   * Test for getting a list of games with a specified status.
   */
  @Test
  public void testGetGameByStatus() {
    //First check that there are no waiting games.
    assertTrue(sut.getGameByStatus("WAITING").getBody().size() == 0);
    Player alice = getPlayer("Alice");
    GameEntity game = sut.addPlayerToGame(alice).getBody();
    //Check that there is only 1 game with waiting status and that game is the one that
    //was just created.
    assertTrue(sut.getGameByStatus("WAITING").getBody().size() == 1);
    assertEquals(game, sut.getGameByStatus("WAITING").getBody().get(0));
    Player bob = getPlayer("Bob");
    game = sut.addPlayerToGame(bob).getBody();
    //Check that there is still only 1 game with waiting status and see if the correct
    //game is being returned.
    assertTrue(sut.getGameByStatus("WAITING").getBody().size() == 1);
    assertEquals(game, sut.getGameByStatus("WAITING").getBody().get(0));
    assertEquals(game.getPlayers().get(0).getName(), "Alice");
  }

  /**
   * Test for changing the status of a game.
   */
  @Test
  public void testChangeGameStatus() {
    Player alice = getPlayer("Alice");
    GameEntity game = sut.addPlayerToGame(alice).getBody();
    assertEquals("WAITING", game.getStatus());
    GameEntity newStatus = new GameEntity();
    newStatus.setStatus("STARTED");
    assertEquals("STARTED", sut.changeGameStatus(game.getId(), newStatus).getBody().getStatus());
  }

  /**
   * Test for changing the status of a game to a forbidden one.
   */
  @Test
  public void testChangeGameStatusForbidden() {
    Player alice = getPlayer("Alice");
    GameEntity game = sut.addPlayerToGame(alice).getBody();
    assertEquals("WAITING", game.getStatus());
    GameEntity newStatus = new GameEntity();
    //started -> waiting
    game.setStatus("STARTED");
    newStatus.setStatus("WAITING");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId(), newStatus).getStatusCode());
    //finished -> started
    newStatus.setStatus("STARTED");
    game.setStatus("FINISHED");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId(), newStatus).getStatusCode());
    //finished -> waiting
    game.setStatus("FINISHED");
    newStatus.setStatus("WAITING");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId(), newStatus).getStatusCode());
    //aborted -> waiting
    game.setStatus("ABORTED");
    newStatus.setStatus("WAITING");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId(), newStatus).getStatusCode());
    //aborted -> started
    game.setStatus("ABORTED");
    newStatus.setStatus("STARTED");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId(), newStatus).getStatusCode());
    //aborted -> finished
    game.setStatus("ABORTED");
    newStatus.setStatus("FINISHED");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId(), newStatus).getStatusCode());
  }

  /**
   * Test for changing the status of a non-existing game.
   */
  @Test
  public void testChangeGameStatusNonEx() {
    Player alice = getPlayer("Alice");
    GameEntity game = sut.addPlayerToGame(alice).getBody();
    assertEquals("WAITING", game.getStatus());
    GameEntity newStatus = new GameEntity();
    newStatus.setStatus("STARTED");
    assertEquals(BAD_REQUEST, sut.changeGameStatus(game.getId() + 1, newStatus).getStatusCode());
  }

  /**
   * Test for getting all the games in repository.
   */
  @Test
  public void testGetAllGames() {
    GameEntity game1 = sut.addPlayerToGame(getPlayer("Alice")).getBody();
    assertTrue(sut.getAllGames().getBody().size() == 1);
    assertEquals(sut.getAllGames().getBody().get(0), game1);
    game1.setStatus("STARTED");
    GameEntity game2 = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    assertTrue(sut.getAllGames().getBody().size() == 2);
    assertEquals(sut.getAllGames().getBody().get(0), game1);
    assertEquals(sut.getAllGames().getBody().get(1), game2);
  }

  /**
   * Test for getting a list of all the questions.
   */
  @Test
  public void testGetAllQuestions() {
    repo.deleteAll();
    GameEntity game = sut.addPlayerToGame(getPlayer("Bob")).getBody();
    Long id = game.getId();
    assertEquals(game.getQuestions(), sut.getAllQuestions(id).getBody());
    assertEquals(BAD_REQUEST, sut.getAllQuestions(id + 1).getStatusCode());
  }

  /**
   * Test for adding a player in singleplayer.
   */
  @Test
  public void testAddSingleplayer() {
    repo.deleteAll();
    GameEntity game1 = sut.addSingleplayer(getPlayer("Alice")).getBody();
    GameEntity game2 = sut.addSingleplayer(getPlayer("Bob")).getBody();
    GameEntity game3 = sut.addSingleplayer(getPlayer("Alice")).getBody();
    assertEquals(game1, repo.findAll().get(0));
    assertEquals(game2, repo.findAll().get(1));
    assertEquals(game3, repo.findAll().get(2));
  }

  /**
   * Test for getting a question by id.
   * For this test to pass you need to have the activities imported!
   */
  @Test
  public void testGetQuestionById() {
    GameEntity game = sut.addPlayerToGame(getPlayer("Alice")).getBody();
    assertEquals(BAD_REQUEST, sut.getQuestionById(game.getId() + 1, 2).getStatusCode());
    assertEquals(BAD_REQUEST, sut.getQuestionById(game.getId(), -3).getStatusCode());
    assertEquals(BAD_REQUEST, sut.getQuestionById(game.getId(), 21).getStatusCode());
    assertEquals(game.getQuestions().get(0), sut.getQuestionById(game.getId(), 1).getBody());
    assertEquals(game.getQuestions().get(3), sut.getQuestionById(game.getId(), 4).getBody());
  }

  /**
   * Tests if you get a bad request when asking for a leaderboard of a nonexistent game.
   */
  @Test
  public void testGetLeaderboardNonExistingGame() {
    assertEquals(BAD_REQUEST, sut.getLeaderboard(10000L).getStatusCode());
  }

  /**
   * Tests if you get a bad request if when asking for a leaderboard of a singleplayer game.
   */
  @Test
  public void testGetLeaderboardSingleplayer() {
    GameEntity game = sut.addSingleplayer(getPlayer("Bob")).getBody();
    assertEquals(BAD_REQUEST, sut.getLeaderboard(game.getId()).getStatusCode());
  }

  /**
   * Tests if you get a good leaderboard from a multiplayer game.
   */
  @Test
  public void testGetLeaderboard() {
    LeaderboardEntry le1 = new LeaderboardEntry("p1", 1);
    LeaderboardEntry le2 = new LeaderboardEntry("p2", 2);
    List<LeaderboardEntry> lb = new ArrayList<LeaderboardEntry>();
    lb.add(le1);
    lb.add(le2);

    Player p1 = new Player("p1");
    Player p2 = new Player("p2");
    p1.setScore(1);
    p2.setScore(2);
    GameEntity game = sut.addPlayerToGame(p1).getBody();
    sut.addPlayerToGame(p2);
    assertEquals(lb, sut.getLeaderboard(game.getId()).getBody());
  }
}

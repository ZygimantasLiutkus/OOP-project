package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import commons.Activity;
import commons.GameEntity;
import commons.Player;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.QuestionRepository;
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
   * @param id the activity's id
   * @param title the title
   * @param consumption_in_wh the consumption
   * @param path the path of the activity
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
    sut = new GameEntityController(repo, playerRepo, qService, qRepo);
  }

  /**
   * Tests that you can't have 2 players with the same name.
   */
  @Test
  public void noPlayersWithSameName() {
    sut.addPlayerToGame(getPlayer("Bob"));
    var actual = sut.addPlayerToGame(getPlayer("Bob"));
    assertEquals(BAD_REQUEST, actual.getStatusCode());
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
    Long id = game.getId() + 1;
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
    Long id  = game.getId();
    assertEquals(alice, sut.getAllPlayers(id).getBody().get(0));
    assertEquals(bob, sut.getAllPlayers(id).getBody().get(1));
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
    newStatus.setStatus("STARTED");
    game.setStatus("FINISHED");
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
}

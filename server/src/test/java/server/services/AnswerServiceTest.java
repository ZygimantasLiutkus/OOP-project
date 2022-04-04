package server.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import commons.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestGameRepository;
import server.database.TestPlayerRepository;

/**
 * Tests for answer service.
 */
class AnswerServiceTest {

  private AnswerService sut;
  private TestGameRepository repo;
  private TestPlayerRepository playerRepo;

  /**
   * Initializes AnswerServiceTest.
   */
  @BeforeEach
  public void setup() {
    repo = new TestGameRepository();
    playerRepo = new TestPlayerRepository();

    sut = new AnswerService(repo, playerRepo);
  }

  /**
   * Tests if the constructor returns a not null object.
   */
  @Test
  public void testConstructor() {
    assertNotNull(sut);
  }

  /**
   * Tests if findPlayer() returns a player if they're in a game.
   */
  @Test
  public void testFindPlayer() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    Player player = new Player();
    game.addPlayer(player);
    repo.save(game);
    assertEquals(player, sut.findPlayer(game, player));
  }

  /**
   * Tests if findPlayer() returns null if the player is not in the game.
   */
  @Test
  public void testFindPlayerFail() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    Player player = new Player();
    repo.save(game);
    assertNotEquals(player, sut.findPlayer(game, player));
    assertNull(sut.findPlayer(game, player));
  }

  /**
   * Tests if findGame() returns a game for a correct id.
   */
  @Test
  public void testFindGame() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    game.setStatus("STARTED");
    repo.save(game);
    long id = game.getId();
    assertEquals(game, sut.findGame(id).getBody());
  }

  /**
   * Tests if findGame() returns a http response 403 (forbidden)
   * if the game status is not "STARTED".
   */
  @Test
  public void testFindGameForbidden() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    game.setStatus("ABORTED");

    repo.save(game);
    long id = game.getId();
    assertEquals(FORBIDDEN, sut.findGame(id).getStatusCode());
  }

  /**
   * Tests if findGame() returns a http response bad request
   * when the game for a specified id does not exist.
   */
  @Test
  public void testFindGameBadRequest() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    repo.save(game);
    Long id = game.getId();
    assertEquals(BAD_REQUEST, sut.findGame(id + 1).getStatusCode());
  }

  /**
   * Tests the outcomes of answerME() method.
   */
  @Test
  public void testAnswerME() {
    Activity a = new Activity("1", "Title", 1, "path");
    Activity b = new Activity("1", "Title", 2, "path");
    Activity c = new Activity("1", "Title", 3, "path");
    Question q = new QuestionMoreExpensive(new ArrayList<>(Arrays.asList(a, b, c)));

    Player player1 = new Player();
    player1.setSelectedAnswer("1");

    Player player2 = new Player();
    player2.setSelectedAnswer("3");

    assertEquals("INCORRECT", sut.answerME(q, player1).getBody().getFeedback());
    assertEquals("CORRECT", sut.answerME(q, player2).getBody().getFeedback());
  }

  /**
   * Tests the outcomes of answerMC() method.
   */
  @Test
  public void testAnswerMC() {
    Activity a = new Activity("1", "Title", 1, "path");
    Activity b = new Activity("1", "Title", 2, "path");
    Activity c = new Activity("1", "Title", 3, "path");
    Question q = new QuestionMoreExpensive(new ArrayList<>(Arrays.asList(a, b, c)));

    Player player1 = new Player();
    player1.setSelectedAnswer("1");

    Player player2 = new Player();
    player2.setSelectedAnswer("3");

    assertEquals("CORRECT", sut.answerMC(q, player1).getBody().getFeedback());
    assertEquals("INCORRECT", sut.answerMC(q, player2).getBody().getFeedback());
  }

  /**
   * Tests the outcomes of answerE() method.
   */
  @Test
  public void testAnswerE() {
    Player player1 = new Player();
    player1.setSelectedAnswer("90");
    Player player2 = new Player();
    player2.setSelectedAnswer("110");
    Player player3 = new Player();
    player3.setSelectedAnswer("150");

    Activity a = new Activity("1", "Title", 100, "path");
    Question q = new QuestionEstimation(new ArrayList<>(Arrays.asList(a)));

    assertEquals("CORRECT", sut.answerE(q, player1).getBody().getFeedback());
    assertEquals("CORRECT", sut.answerE(q, player2).getBody().getFeedback());
    assertEquals("INCORRECT", sut.answerE(q, player3).getBody().getFeedback());
  }
}
package server.api;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.services.QuestionService;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

  private static Player getPlayer(String s) {
    return new Player(s);
  }

  /**
   * Initializes GameEntityControllerTest.
   */
  @BeforeEach
  public void setUp() {
    repo = new TestGameRepository();
    playerRepo =  new TestPlayerRepository();
    activityRepo = new TestActivityRepository();
    random = new Random();
    System.out.println(random.nextInt());
    qService = new QuestionService(activityRepo, random);
    sut = new GameEntityController(repo, playerRepo, qService);
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

  @Test
  public void databaseIsUsed() {
    sut.addPlayerToGame(getPlayer("Alice"));
    assertTrue(repo.used.contains("saved"));
  }
}

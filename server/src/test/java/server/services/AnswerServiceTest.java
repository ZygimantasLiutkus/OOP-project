package server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import commons.GameEntity;
import commons.Player;
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
   * Tests if updating a player in a non-existing game doesn't work.
   */
  @Test
  public void testUpdateScoreNonExistentGame() {
    repo.deleteAll();
    assertEquals(BAD_REQUEST, sut.updateScore(0L, new Player("test")).getStatusCode());
  }

  /**
   * Tests if updating a player in a non-started game doesn't work.
   */
  @Test
  public void testUpdateScoreNonStartedGame() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    game = repo.save(game);
    assertEquals(FORBIDDEN, sut.updateScore(game.getId(), new Player("test")).getStatusCode());
  }

  /**
   * Tests if updating a player in a started game does work.
   */
  @Test
  public void testUpdateScoreStartedGame() {
    repo.deleteAll();
    GameEntity game = new GameEntity();
    Player player = new Player("test");
    game.addPlayer(player);
    game.setStatus("STARTED");
    game = repo.save(game);
    assertEquals(player, sut.updateScore(game.getId(), player).getBody());
  }
}
package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests answer class.
 */
class AnswerTest {

  /**
   * Tests getFeedback() method.
   */
  @Test
  void getFeedback() {
    Player player = new Player("test");
    Answer ans = new Answer("CORRECT", player, 1, 2);

    assertEquals("CORRECT", ans.getFeedback());
  }

  /**
   * Fails getFeedback() method.
   */
  @Test
  void getFeedBackFail() {
    Player player = new Player("test");
    Answer ans = new Answer("INCORRECT", player, 1, 2);

    assertNotEquals("CORRECT", ans.getFeedback());
  }

  /**
   * Tests getPlayer() method.
   */
  @Test
  void getPlayer() {
    Player player = new Player("test");
    Answer ans = new Answer("OK", player, 1, 2);
    assertEquals(player, ans.getPlayer());
  }

  /**
   * Fails getPlayer() method.
   */
  @Test
  void getPlayerFail() {
    Player player = new Player("test");
    Answer ans = new Answer("OK", player, 1, 2);
    Player player1 = new Player("test1");

    assertNotEquals(player1, ans.getPlayer());
  }

  /**
   * Tests getScore() method.
   */
  @Test
  void getScore() {
    Player player = new Player("test");
    Answer ans = new Answer("OK", player, 1, 2);

    assertEquals(1, ans.getScore());
  }

  /**
   * Tests getAdded() method.
   */
  @Test
  void getAdded() {
    Player player = new Player("test");
    Answer ans = new Answer("OK", player, 1, 2);

    assertEquals(2, ans.getAdded());
  }
}
package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test class for message.
 */
public class MessageTest {

  /**
   * Test method for the constructor.
   */
  @Test
  public void testConstructor() {
    Message testMessage = new Message("emoji", "player");
    assertEquals("emoji", testMessage.getText());
    assertEquals("player", testMessage.getPlayerName());
  }

  /**
   * Test for empty constructor.
   */
  @Test
  void emptyConstr() {
    Message m = new Message();
    assertNotNull(m);
  }

  /**
   * Test method for the setPlayerName method.
   */
  @Test
  public void testSetPlayerName() {
    Message testMessage = new Message("emoji", "player");
    testMessage.setPlayerName("player2");
    assertEquals("player2", testMessage.getPlayerName());
  }

  /**
   * Test method for the setEmojiName method.
   */
  @Test
  public void testSetEmojiName() {
    Message testMessage = new Message("emoji", "player");
    testMessage.setText("emoji2");
    assertEquals("emoji2", testMessage.getText());
  }
}

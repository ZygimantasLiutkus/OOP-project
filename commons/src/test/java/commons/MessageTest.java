package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    assertEquals("emoji", testMessage.getEmojiName());
    assertEquals("player", testMessage.getPlayerName());
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
    testMessage.setEmojiName("emoji2");
    assertEquals("emoji2", testMessage.getEmojiName());
  }
}

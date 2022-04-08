package server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the server controller.
 */
public class ServerControllerTest {

  public ServerController sc;

  /**
   * Initializes the server controller.
   */
  @BeforeEach
  public void setup() {
    sc = new ServerController();
  }

  /**
   * Tests the output of the index.
   */
  @Test
  public void testIndex() {
    assertEquals("This is a working game server", sc.index());
  }
}

package server;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the websocket config.
 */
public class WebsocketConfigTest {

  public WebsocketConfig wsc;

  /**
   * Initializes the websocket config.
   */
  @BeforeEach
  public void setup() {
    this.wsc = new WebsocketConfig();
  }

  /**
   * Tests if the websocketconfig is set.
   */
  @Test
  public void testConstructor() {
    assertNotNull(this.wsc);
  }
}

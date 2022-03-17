package client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The main controller test class of the client application.
 */
public class ServerUtilsTest {

  private ServerUtils sut;

  /**
   * Initializes the main controller.
   */
  @BeforeEach
  public void setup() {
    sut = new ServerUtils();
    Player pl = new Player("test");
    sut.setPlayer(pl);
  }

  /**
   * This test can only be run when the server runs, so it's commented out by default.
   */
  @Test
  public void setServerTest() {
    assertTrue(true);
    //This test can only be run when the server runs, so it's commented out by default
    //assertTrue(sut.setServer("localhost:8080"));
    //assertTrue(sut.setServer("http://localhost:8080"));
  }

  /**
   * This test can only be run when the server runs, so it's commented out by default.
   */
  @Test
  public void setServerTestFail() {
    assertFalse(sut.setServer("http//localhost:8080"));
    assertFalse(sut.setServer("https//localhost:8080"));
    assertFalse(sut.setServer("wss://localhost:8080"));
  }

  /**
   * Tests the noAnwer method. 0 by default so test should return true!
   */
  @Test
  public void noAnswerTest() {
    assertTrue(sut.noAnswer());
  }

  /**
   * Tests reset answer method.
   */
  @Test
  public void resetAnswer() {
    sut.getPlayer().setSelectedAnswer("3");
    sut.resetAnswer();
    assertEquals("0", sut.getPlayer().getSelectedAnswer());
  }
}
package client.scenes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the multiple choice game screen.
 */
public class MultipleChoiceCtrlTest {
  private MultipleChoiceCtrl sut;
  private ServerUtils server;
  private MainCtrl mainCtrl;

  /**
   * Setup method for the controller.
   */
  @BeforeEach
  void setUp() {
    server = new ServerUtils();
    mainCtrl = new MainCtrl();
    sut = new MultipleChoiceCtrl(server, mainCtrl);
  }

  /**
   * Test for reset timer method.
   */
  @Test
  void resetTimerTest() {
    assertEquals("10 s", sut.getTimeCounter());
  }
}

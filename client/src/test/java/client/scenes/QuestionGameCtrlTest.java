package client.scenes;

import client.utils.ServerUtils;
import client.utils.TimerUtils;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the multiple choice game screen.
 */
public class QuestionGameCtrlTest {
  private QuestionGameCtrl sut;
  private ServerUtils server;
  private TimerUtils timers;
  private MainCtrl mainCtrl;

  /**
   * Setup method for the controller.
   */
  @BeforeEach
  void setUp() {
    server = new ServerUtils();
    timers = new TimerUtils();
    mainCtrl = new MainCtrl();
    sut = new QuestionGameCtrl(server, timers, mainCtrl);
  }
}
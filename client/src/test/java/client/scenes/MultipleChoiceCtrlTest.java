package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;

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
}
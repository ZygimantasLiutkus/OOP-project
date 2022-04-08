package client.scenes;

import client.utils.ServerUtils;
import client.utils.TimerUtils;
import org.junit.jupiter.api.BeforeEach;

/**
 * The test class for leaderboard screen controller class.
 */
class LeaderboardScreenCtrlTest {

  private LeaderboardScreenCtrl leaderboardScreenCtrl;
  private ServerUtils server;
  private TimerUtils timerUtils;
  private MainCtrl mainCtrl;
  private QuestionGameCtrl questionGameCtrl;
  private NamePopupCtrl nameCtrl;

  /**
   * Setup method for the controller.
   */
  @BeforeEach
  void setUp() {
    this.server = new ServerUtils();
    this.mainCtrl = new MainCtrl();
    this.timerUtils = new TimerUtils();
    this.questionGameCtrl = new QuestionGameCtrl(server, timerUtils, mainCtrl);
    this.leaderboardScreenCtrl = new LeaderboardScreenCtrl(server, mainCtrl, questionGameCtrl,
        nameCtrl);
  }
}
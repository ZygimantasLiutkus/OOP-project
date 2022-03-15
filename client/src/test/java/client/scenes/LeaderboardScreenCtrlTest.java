package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;

/**
 * The test class for leaderboard screen controller class.
 */
class LeaderboardScreenCtrlTest {

  private LeaderboardScreenCtrl leaderboardScreenCtrl;
  private ServerUtils server;
  private MainCtrl mainCtrl;

  /**
   * Setup method for the controller.
   */
  @BeforeEach
  void setUp() {
    this.server = new ServerUtils();
    this.mainCtrl = new MainCtrl();
    this.leaderboardScreenCtrl = new LeaderboardScreenCtrl(server, mainCtrl);
  }
}
package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.inject.Inject;

/**
 * Controller for the WaitingRoom scene.
 */
public class WaitingRoomCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  private Button startButton;

  /**
   * Construct for the Waiting Room Controller.
   *
   * @param server reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject
  public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  /**
   * Starts the game in single-player mode.
   */
  public void startSinglePlayer() {
    mainCtrl.showMoreExpensive();
  }
}

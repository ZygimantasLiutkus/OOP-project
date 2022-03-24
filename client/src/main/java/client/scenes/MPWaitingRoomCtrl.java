package client.scenes;

import client.utils.ServerUtils;
import commons.GameEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.inject.Inject;

/**
 * Controller for the Single-player WaitingRoom scene.
 */
public class MPWaitingRoomCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  private Button startButton;

  /**
   * Constructor for the Multi-player Waiting Room Controller.
   *
   * @param server reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject
  public MPWaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  /**
   * Starts the game in multi-player mode.
   */
  public void startMultiPlayer() {
    mainCtrl.showMoreExpensive(GameEntity.Type.MULTIPLAYER);
  }
}

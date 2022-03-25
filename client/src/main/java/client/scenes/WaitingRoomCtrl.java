package client.scenes;

import client.utils.ServerUtils;
import commons.GameEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.inject.Inject;

/**
 * Controller for the Single-player WaitingRoom scene.
 */
public class WaitingRoomCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final MultipleChoiceCtrl multipleCtrl;

  @FXML
  private Button startButton;

  /**
   * Constructor for the Single-player Waiting Room Controller.
   *
   * @param server       reference to the server the game will run on.
   * @param mainCtrl     reference to the main controller.
   * @param multipleCtrl reference to multiple choice controller.
   */
  @Inject
  public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl, MultipleChoiceCtrl multipleCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multipleCtrl = multipleCtrl;
  }

  /**
   * Starts the game in single-player mode.
   */
  public void startSinglePlayer() {
    mainCtrl.showMoreExpensive(GameEntity.Type.SINGLEPLAYER);
    multipleCtrl.timerStart();
  }
}

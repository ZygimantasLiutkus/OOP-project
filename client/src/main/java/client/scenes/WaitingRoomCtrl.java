package client.scenes;

import client.utils.ServerUtils;
import commons.GameEntity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javax.inject.Inject;

/**
 * Controller for the Single-player WaitingRoom scene.
 */
public class WaitingRoomCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final QuestionGameCtrl multipleCtrl;

  private Timeline dots;

  @FXML
  private Button startButton;

  @FXML
  private Label label;

  /**
   * Constructor for the Single-player Waiting Room Controller.
   *
   * @param server       reference to the server the game will run on.
   * @param mainCtrl     reference to the main controller.
   * @param multipleCtrl reference to multiple choice controller.
   */
  @Inject
  public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl, QuestionGameCtrl multipleCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multipleCtrl = multipleCtrl;
  }

  /**
   * Animation for waiting room.
   */
  public void animate() {
    label.setText("Waiting to start");
    dots = new Timeline(new KeyFrame(Duration.millis(400), actionEvent -> {
      if (label.getText().contains("...")) {
        label.setText(label.getText().substring(0, label.getText().length() - 3));
      } else {
        label.setText(label.getText() + ".");
      }
    }));
    dots.setCycleCount(Animation.INDEFINITE);
    dots.play();
  }

  /**
   * Starts the game in single-player mode.
   */
  public void startSinglePlayer() {
    dots.stop();
    mainCtrl.showCountdown(GameEntity.Type.SINGLEPLAYER);

  }

  /**
   * Have option to bgo back from a single player game.
   */
  public void goHome() {
    dots.stop();
    mainCtrl.showChooseScreen();
  }
}

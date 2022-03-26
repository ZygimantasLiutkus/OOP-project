package client.scenes;

import client.utils.ServerUtils;
import commons.GameEntity;
import commons.Player;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javax.inject.Inject;

/**
 * Controller for the Single-player WaitingRoom scene.
 */
public class MPWaitingRoomCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final MultipleChoiceCtrl multipleChoiceCtrl;

  @FXML
  private Button startButton;

  /**
   * Constructor for the Multi-player Waiting Room Controller.
   *
   * @param server             reference to the server the game will run on.
   * @param mainCtrl           reference to the main controller.
   * @param multipleChoiceCtrl reference to multiple choice controller
   */
  @Inject
  public MPWaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl,
                           MultipleChoiceCtrl multipleChoiceCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multipleChoiceCtrl = multipleChoiceCtrl;

    new Timer().scheduleAtFixedRate(new TimerTask() {
      /**
       * Run method that gets executed once every second.
       */
      @Override
      public void run() {
        if (checkPlayerNo()) {
          startButton.setDisable(false);
          startButton.setStyle("-fx-background-color: #11AD31");
        } else {
          startButton.setDisable(true);
          startButton.setStyle("-fx-background-color: #B3B3B3");
        }
      }
    }, 0, 1000);
  }

  /**
   * Method that checks whether the multiplayer game can be started.
   *
   * @return a boolean
   */
  public boolean checkPlayerNo() {
    return (server.getGame().getPlayers().size() > 1);
  }

  /**
   * Starts the game in multi-player mode.
   */
  public void startMultiPlayer() {
    mainCtrl.showMoreExpensive(GameEntity.Type.MULTIPLAYER);
    multipleChoiceCtrl.timerStart();
  }

  /**
   * Have the ability to leave a multiplayer lobby.
   */
  public void goHome() {
    List<Player> players = this.server.getGame().getPlayers();
    players.remove(server.getPlayer());
    this.server.updatePlayer(players);
    mainCtrl.showChooseScreen();
  }
}

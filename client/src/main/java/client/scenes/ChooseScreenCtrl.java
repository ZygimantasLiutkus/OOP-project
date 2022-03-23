package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.GameEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * A template controller for the ChooseScreen scene.
 */
public class ChooseScreenCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  private Button singleplayerButton;

  @FXML
  private Button leaderboardButton;

  @FXML
  private Button multiplayerButton;

  @FXML
  private Button changeServerButton;

  @FXML
  private Button changeNameButton;

  /**
   * Constructor for ChooseScreenCtrl.
   *
   * @param server   reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject

  public ChooseScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  /**
   * Shows the singleplayer waiting room.
   */
  public void playSinglePlayer() {
    if (server.getPlayer().getName().equals("")) {
      mainCtrl.showNamePopup(NextScreen.WaitingRoomScreen);
    } else {
      mainCtrl.showWaitingRoomScreenSP();
    }
  }

  /**
   * Shows the global (singleplayer) leaderboard of current server.
   */
  public void leaderboard() {
    mainCtrl.showSPLeaderboard(null);
  }

  /**
   * Shows the multiplayer waiting room.
   */
  public void playMultiplayer() {
    if (server.getPlayer().getName().equals("")) {
      mainCtrl.showNamePopup(NextScreen.MultiPlayerWaitingRoom);
    } else {
      mainCtrl.showMoreExpensive(
          GameEntity.Type.SINGLEPLAYER); // TODO: Change to singleplayer waiting room
    }
  }

  /**
   * Shows the entry screen so that the player can enter another server.
   */
  public void changeServer() {
    mainCtrl.showEntry();
  }

  /**
   * Opens the name popup so that the player can enter another name.
   */
  public void changeName() {
    mainCtrl.showNamePopup(NextScreen.None);
  }
}

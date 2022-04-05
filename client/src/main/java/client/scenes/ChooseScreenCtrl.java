package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * A template controller for the ChooseScreen scene.
 */
public class ChooseScreenCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final NamePopupCtrl namePopupCtrl;

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
   * @param server        reference to the server the game will run on.
   * @param mainCtrl      reference to the main controller.
   * @param namePopupCtrl reference to the name popup controller.
   */
  @Inject

  public ChooseScreenCtrl(ServerUtils server, MainCtrl mainCtrl, NamePopupCtrl namePopupCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.namePopupCtrl = namePopupCtrl;
  }

  /**
   * Shows the singleplayer waiting room.
   */
  public void playSinglePlayer() {
    namePopupCtrl.initializeName();
    if (server.getDummyPlayer().getName().equals("")) {
      mainCtrl.showNamePopup(NextScreen.WaitingRoomScreen);
    } else {
      server.addSingleplayer();
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
    namePopupCtrl.initializeName();
    if (server.getDummyPlayer().getName().equals("")) {
      mainCtrl.showNamePopup(NextScreen.MPWaitingRoomScreen);
    } else {
      verifyName();
    }
  }


  /**
   * Verifies if the name is already used.
   */
  public void verifyName() {
    Player player = server.addPlayer();
    if (player == null) {
      namePopupCtrl.setErrorText("This name is already taken, please choose another name");
      mainCtrl.showNamePopup(NextScreen.MPWaitingRoomScreen);
      namePopupCtrl.showErrorText(true);
    } else {
      mainCtrl.showWaitingRoomScreenMP();
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

  /**
   * Method to transition to activity panel.
   */
  public void goPanel() {
    mainCtrl.showActivityOverview();
  }
}

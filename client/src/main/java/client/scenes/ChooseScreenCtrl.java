package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * A template controller for the ChooseScreen scene.
 */
public class ChooseScreenCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final NamePopupCtrl namePopupCtrl;
  public Scanner scanner;
  public StringBuffer buffer = new StringBuffer();

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
    initializeName();
    if (server.getDummyPlayer().getName().equals("")) {
      if (scanner.hasNext()) {
        String name = scanner.next();
        server.setDummy(new Player(name));
        buffer.append(name);
      } else {
        mainCtrl.showNamePopup(NextScreen.WaitingRoomScreen);
      }
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
    initializeName();
    if (server.getPlayer().getName().equals("")) {
      if (scanner.hasNext()) {
        String name = scanner.next();
        server.setDummy(new Player(name));
        buffer.append(name);
      } else {
        mainCtrl.showNamePopup(NextScreen.MPWaitingRoomScreen);
      }
    } else {
      Player player = server.addPlayer();
      if (player == null) {
        namePopupCtrl.setErrorText("This name is already taken, please choose another name");
        namePopupCtrl.showErrorText(true);
        mainCtrl.showNamePopup(NextScreen.MPWaitingRoomScreen);
      } else {
        mainCtrl.showWaitingRoomScreenMP();
      }
    }
  }

  public void initializeName() {
    try {
      scanner = new Scanner(new File("client/src/main/resources/name.txt"));
    } catch (FileNotFoundException e) {
      File file = new File("client/src/main/resources/name.txt");
      try {
        file.createNewFile();
        scanner = new Scanner(file);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
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

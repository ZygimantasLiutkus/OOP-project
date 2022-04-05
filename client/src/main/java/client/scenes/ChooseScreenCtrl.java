package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A template controller for the ChooseScreen scene.
 */
public class ChooseScreenCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final NamePopupCtrl namePopupCtrl;

  @FXML
  private Label label;

  @FXML
  private Button singleplayerButton;

  @FXML
  private Button leaderboardButton;

  @FXML
  private Button multiplayerButton;

  @FXML
  private Button changeServerButton;

  @FXML
  private Button adminButton;

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
   * Animation for the choose screen.
   */
  public void animate() {
    TranslateTransition labelT = new TranslateTransition(Duration.millis(500), label);
    labelT.setFromY(-160);
    labelT.setToY(0);
    labelT.play();

    TranslateTransition sp = new TranslateTransition(Duration.millis(300), singleplayerButton);
    sp.setFromY(300);
    sp.setToY(0);


    TranslateTransition l = new TranslateTransition(Duration.millis(300), leaderboardButton);
    l.setFromY(300);
    l.setToY(0);

    TranslateTransition mp = new TranslateTransition(Duration.millis(300), multiplayerButton);
    mp.setFromY(300);
    mp.setToY(0);

    TranslateTransition cs = new TranslateTransition(Duration.millis(300), changeServerButton);
    cs.setFromY(150);
    cs.setToY(0);

    TranslateTransition cn = new TranslateTransition(Duration.millis(300), changeNameButton);
    cn.setFromY(150);
    cn.setToY(0);

    TranslateTransition a = new TranslateTransition(Duration.millis(300), adminButton);
    a.setFromY(150);
    a.setToY(0);

    Animation flash = new Transition() {
      {
        setCycleDuration(Duration.millis(1500));
        setCycleCount(INDEFINITE);
        setInterpolator(Interpolator.EASE_OUT);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double frac) {
        Color color = new Color(1, 1, 0, Math.abs(Math.abs(-1.0 + 2 * frac * frac) - 1.0));

        DropShadow shadow = new DropShadow();
        shadow.setSpread(0.75);
        Node target = label;
        shadow.setColor(color);
        target.setEffect(shadow);
      }
    };

    ParallelTransition sq = new ParallelTransition(sp, l, mp, cs, cn, a, flash);
    sq.setDelay(Duration.millis(1500));
    sq.play();
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

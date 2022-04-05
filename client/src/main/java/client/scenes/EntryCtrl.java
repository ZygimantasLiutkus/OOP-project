package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A template controller for the EntryScreen scene.
 */
public class EntryCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  public Label titleLabel;

  @FXML
  public FlowPane centralFlow;

  @FXML
  public Label bottomLabel;

  @FXML
  public Button submitButton;

  @FXML
  public Label invalidIPLabel;

  @FXML
  public TextField ipField;

  /**
   * Constructor for EntryCtrl.
   *
   * @param server   reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject
  public EntryCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  /**
   * Animations for entry screen.
   */
  public void animate() {
    TranslateTransition translate1 = new TranslateTransition();
    translate1.setNode(titleLabel);
    translate1.setDuration(Duration.millis(1000));
    translate1.setFromY(-150);
    translate1.setToY(0);

    TranslateTransition translate2 = new TranslateTransition();
    translate2.setNode(centralFlow);
    translate2.setDuration(Duration.millis(1000));
    translate2.setFromY(-450);
    translate2.setToY(0);

    TranslateTransition translate3 = new TranslateTransition();
    translate3.setNode(bottomLabel);
    translate3.setDuration(Duration.millis(1000));
    translate3.setFromY(-900);
    translate3.setToY(0);

    Animation flash = new Transition() {
      {
        setCycleDuration(Duration.millis(600));
        setInterpolator(Interpolator.EASE_IN);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double frac) {
        Color color = new Color(1, 1, 0, Math.abs(Math.abs(-1.0 + 2 * frac) - 1.0));

        DropShadow shadow = new DropShadow();
        shadow.setSpread(0.75);
        Node target = submitButton;
        shadow.setColor(color);
        target.setEffect(shadow);
      }
    };

    flash.setDelay(Duration.millis(500));
    SequentialTransition sq = new SequentialTransition(translate1, translate2, translate3, flash);
    sq.setDelay(Duration.millis(500));
    sq.play();
  }

  /**
   * Submits the ipAddress and checks if there is a game server running on that ip.
   * If there is, go to the next screen, if there isn't display an error message.
   */
  public void submit() {
    String ip = ipField.getText();
    boolean success = server.setServer(ip);
    if (success) {
      invalidIPLabel.setVisible(false);
      mainCtrl.showChooseScreen();
    } else {
      invalidIPLabel.setVisible(true);
      ipField.textProperty().addListener((observable) -> {
        invalidIPLabel.setVisible(false);
      });
    }
  }

  /**
   * Checks for an enter or escape key press.
   *
   * @param e the KeyEvent which indicates which key is pressed
   */
  public void keyPressed(KeyEvent e) {
    switch (e.getCode()) {
      case ENTER:
        submit();
        break;
      default:
        break;
    }
  }

}

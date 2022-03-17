package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * A template controller for the EntryScreen scene.
 */
public class EntryCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

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

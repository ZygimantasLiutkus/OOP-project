package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.GameEntity;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Template controller for NamePopup scene.
 */
public class NamePopupCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final MultipleChoiceCtrl multipleCtrl;

  private NextScreen nextScreen;

  @FXML
  private TextField nameField;

  @FXML
  private Label takenNameLabel;

  @FXML
  private Button nameButton;

  /**
   * Constructor for NamePopupCtrl.
   *
   * @param server       reference to the server the game will run on.
   * @param mainCtrl     reference to the main controller.
   * @param multipleCtrl reference to multiple controller.
   */
  @Inject
  public NamePopupCtrl(ServerUtils server, MainCtrl mainCtrl,
                       MultipleChoiceCtrl multipleCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multipleCtrl = multipleCtrl;
  }

  /**
   * Saves the entered name of the user.
   */
  public void submit() {
    if (!nameField.getText().equals("")) {
      server.setDummy(new Player(nameField.getText()));
      server.setPlayer(server.addSingleplayer(server.getDummyPlayer()));
      switch (nextScreen) {
        case WaitingRoomScreen:
          mainCtrl.showWaitingRoomScreenSP();
          break;
        case MultiPlayerWaitingRoom:
          // TODO: Add check if this name is valid to enter the multiplayer waiting room
          // TODO: Show multiplayer waiting room
          mainCtrl.showMoreExpensive(GameEntity.Type.MULTIPLAYER);
          break;
        default:
          break;
      }

      mainCtrl.closeNamePopup();
    } else {
      takenNameLabel.setText("Please enter a name first");
      takenNameLabel.setVisible(true);
      nameField.textProperty().addListener((observable) -> {
        takenNameLabel.setVisible(false);
        takenNameLabel.setText("This name is already taken");
      });
    }
  }

  /**
   * Sets the screen to be shown after the name was entered.
   *
   * @param nextScreen the screen to be shown after the name was entered
   */
  public void setNextScreen(NextScreen nextScreen) {
    this.nextScreen = nextScreen;
  }

  /**
   * Sets the error text of the name popup.
   *
   * @param text the error text to show on the name popup
   */
  public void setErrorText(String text) {
    takenNameLabel.setText(text);
  }

  /**
   * Sets the visibility of the error text.
   *
   * @param show true if the text needs to be shown, false otherwise
   */
  public void showErrorText(boolean show) {
    takenNameLabel.setVisible(show);
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
      case ESCAPE:
        mainCtrl.closeNamePopup();
        break;
      default:
        break;
    }
  }
}

package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
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
  public FileWriter writer;

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
   * @param server   reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject
  public NamePopupCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  /**
   * Initializes the scanner and may potentially create the file.
   */
  public void initializeName() {
    try {
      Scanner scanner = new Scanner(new File("name.txt"));
      String name = scanner.nextLine();
      server.setDummy(new Player(name));
      nameField.setText(name);
    } catch (Exception e) {
      server.setDummy(new Player(""));
    }
  }

  /**
   * Saves the entered name of the user.
   */
  public void submit() {
    if (nameField.getText().equals("")) {
      setErrorText("Please enter a name first");
      incorrectName();
      return;
    }

    server.setDummy(new Player(nameField.getText()));
    //Overwrite the already existent name inside the .txt
    try {
      writer = new FileWriter("name.txt", false);
      writer.write(nameField.getText());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    switch (nextScreen) {
      case WaitingRoomScreen:
        server.addSingleplayer();
        mainCtrl.showWaitingRoomScreenSP();
        break;

      case MPWaitingRoomScreen:
        Player player = server.addPlayer();

        if (player == null) {
          setErrorText("This name is already taken, please choose another name");
          incorrectName();
          return;
        }
        mainCtrl.showWaitingRoomScreenMP();
        break;

      default:
        break;
    }

    mainCtrl.closeNamePopup();
  }

  /**
   * Shows the error text until the name is changed.
   */
  public void incorrectName() {
    showErrorText(true);
    nameField.textProperty().addListener((observable) -> {
      showErrorText(false);
    });
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

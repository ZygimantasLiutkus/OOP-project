package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Template controller for NamePopup scene.
 */
public class NamePopupCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  private TextField nameField;

  @FXML
  private Label takenNameLabel;

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

}

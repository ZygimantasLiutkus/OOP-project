package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

}

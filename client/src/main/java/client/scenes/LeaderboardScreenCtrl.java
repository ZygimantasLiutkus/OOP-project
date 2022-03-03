package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

/**
 * Template controller for the LeaderboardScreen scene.
 */
public class LeaderboardScreenCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  private TableColumn player;

  @FXML
  private TableColumn score;

  @FXML
  private Label scoreLabel;

  /**
   * Constructor for LeaderboardScreenCtrl.
   *
   * @param server   reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject
  public LeaderboardScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

}

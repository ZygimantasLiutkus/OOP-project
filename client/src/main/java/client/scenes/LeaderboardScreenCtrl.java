package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.LeaderboardEntry;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Template controller for the LeaderboardScreen scene.
 */
public class LeaderboardScreenCtrl implements Initializable {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  private ObservableList<LeaderboardEntry> data;

  @FXML
  private TableView leaderboardTable;

  @FXML
  private TableColumn name;

  @FXML
  private TableColumn score;

  @FXML
  private Label scoreLabel;

  @FXML
  private Button reconnectButton;

  @FXML
  private Button homeButton;

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

  /**
   * {@InheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    name.setCellValueFactory(new PropertyValueFactory<LeaderboardEntry, String>("name"));
    score.setCellValueFactory(new PropertyValueFactory<LeaderboardEntry, Integer>("score"));
  }

  /**
   * Updates the global leaderboard with stored leaderboard entries.
   */
  public void refreshTop5() {
    List<LeaderboardEntry> entries = server.getLeaderboardEntries();
    Collections.sort(entries, (e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));

    List<LeaderboardEntry> leaders = new ArrayList<>();
    if (entries.size() >= 5) {
      leaders = entries.subList(0, 5);
    } else {
      for (int i = 0; i < entries.size(); i++) {
        leaders.add(entries.get(i));
      }
    }

    data = FXCollections.observableList(leaders);
    leaderboardTable.setItems(data);
  }

  /**
   * Setter for the label for a singleplayer / global leaderboard.
   */
  public void setSingleplayer() {
    this.scoreLabel.setText("Global Scores");
    this.reconnectButton.setVisible(false);
    this.homeButton.setVisible(false);
  }

  /**
   * Setter for the label for a multiplayer leaderboard.
   */
  public void setMultiplayer() {
    this.scoreLabel.setText("Scores");
  }

  public void home() {
    mainCtrl.showChooseScreen();
  }
}

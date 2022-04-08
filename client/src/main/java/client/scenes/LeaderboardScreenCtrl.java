package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.GameEntity;
import commons.LeaderboardEntry;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Template controller for the LeaderboardScreen scene.
 */
public class LeaderboardScreenCtrl implements Initializable {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final QuestionGameCtrl questionGameCtrl;
  private final NamePopupCtrl nameCtrl;

  private ObservableList<LeaderboardEntry> data;
  private LeaderboardEntry ownEntry;
  private GameEntity.Type gameType;

  @FXML
  private TableView leaderboardTable;

  @FXML
  private TableColumn ranking;

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
   * @param server           reference to the server the game will run on.
   * @param mainCtrl         reference to the main controller.
   * @param questionGameCtrl reference to the questionGame controller.
   * @param nameCtrl         reference to the namePopUp controller.
   */
  @Inject

  public LeaderboardScreenCtrl(ServerUtils server, MainCtrl mainCtrl,
                               QuestionGameCtrl questionGameCtrl,
                               NamePopupCtrl nameCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.questionGameCtrl = questionGameCtrl;
    this.nameCtrl = nameCtrl;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    leaderboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    ranking.setCellValueFactory(new PropertyValueFactory<LeaderboardEntry, Integer>("ranking"));
    name.setCellValueFactory(new PropertyValueFactory<LeaderboardEntry, String>("name"));
    score.setCellValueFactory(new PropertyValueFactory<LeaderboardEntry, Integer>("score"));
  }

  /**
   * Updates the global leaderboard with stored leaderboard entries.
   */
  public void refreshTop10() {
    List<LeaderboardEntry> entries;
    if (this.gameType == GameEntity.Type.SINGLEPLAYER) {
      entries = server.getGlobalLeaderboard();
    } else {
      entries = server.getMultiplayerLeaderboard();
    }
    entries.sort((e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));

    for (int i = 0; i < entries.size(); i++) {
      LeaderboardEntry entry = entries.get(i);
      entry.setRanking(i + 1);
      if (ownEntry != null && ownEntry.getName().equals(entry.getName())) {
        ownEntry = entry;
      }
    }

    List<LeaderboardEntry> leaders;

    if (entries.size() > 10) {
      leaders = entries.subList(0, 10);
      if (ownEntry != null && !leaders.contains(ownEntry)) {
        leaders.add(ownEntry);
      }
    } else {
      leaders = new ArrayList<>(entries);
    }

    data = FXCollections.observableList(leaders);
    this.updateRowFactory();
    leaderboardTable.setItems(data);
  }

  /**
   * Updates the rowFactory of the leaderboard table.
   * This sets the background colour of the current player's entry in the table to red.
   */
  private void updateRowFactory() {
    leaderboardTable.setRowFactory(tv -> new TableRow<LeaderboardEntry>() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void updateItem(LeaderboardEntry item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setStyle("");
        } else if (ownEntry != null && item.getName().equals(ownEntry.getName())) {
          setStyle("-fx-background-color: tomato;");
        } else {
          setStyle("");
        }
      }
    });
  }

  /**
   * Setter for the settings of a singleplayer / global leaderboard.
   *
   * @param entry the leaderboardEntry of the current player (null if the player didn't play a game)
   */
  public void setSingleplayer(LeaderboardEntry entry) {
    this.scoreLabel.setText("Global Scores");
    this.reconnectButton.setVisible(false);
    this.homeButton.setVisible(false);
    this.gameType = GameEntity.Type.SINGLEPLAYER;
    this.ownEntry = entry;
    this.refreshTop10();
  }

  /**
   * Setter for the settings of a multiplayer leaderboard.
   *
   * @param entry the leaderboardEntry of the current player
   */
  public void setMultiplayer(LeaderboardEntry entry) {
    this.scoreLabel.setText("Scores");
    this.reconnectButton.setVisible(true);
    this.homeButton.setVisible(true);
    this.gameType = GameEntity.Type.MULTIPLAYER;
    this.ownEntry = entry;
    this.refreshTop10();
  }

  /**
   * Set intermediate leaderboard.
   *
   * @param entry leaderboard entry.
   */
  public void setIntermediate(LeaderboardEntry entry) {
    this.scoreLabel.setText("Scores");
    this.reconnectButton.setVisible(false);
    this.homeButton.setVisible(false);
    this.gameType = GameEntity.Type.MULTIPLAYER;
    this.ownEntry = entry;
    this.refreshTop10();
  }

  /**
   * Returns from the leaderboard screen to the home screen.
   */
  public void home() {
    mainCtrl.showChooseScreen();
    if (gameType.equals(GameEntity.Type.MULTIPLAYER)) {
      questionGameCtrl.disconnect();
    }
  }

  /**
   * Setter for the text of the score label.
   *
   * @param text the text for the score label.
   */
  public void setScoreLabel(String text) {
    this.scoreLabel.setText(text);
  }

  /**
   * Reconnect back to a game.
   */
  public void reconnect() {
    if (server.addPlayer() != null) {
      mainCtrl.showWaitingRoomScreenMP();
    } else {
      nameCtrl.setErrorText("This name is already taken, please choose another name");
      mainCtrl.showNamePopup(NextScreen.MPWaitingRoomScreen);
      nameCtrl.showErrorText(true);
    }
  }

  /**
   * Method to show buttons for end leaderboard.
   */
  public void enableHomeButtons() {
    this.reconnectButton.setVisible(true);
    this.homeButton.setVisible(true);
  }

  /**
   * Method to hide buttons for end leaderboard.
   */
  public void disableHomeButtons() {
    this.reconnectButton.setVisible(false);
    this.homeButton.setVisible(false);
  }
}

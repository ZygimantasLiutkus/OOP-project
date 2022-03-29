package client.scenes;

import client.utils.ServerUtils;
import commons.GameEntity;
import commons.Player;
import java.net.URL;
import java.util.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import javax.inject.Inject;

/**
 * Controller for the Single-player WaitingRoom scene.
 */
public class MPWaitingRoomCtrl implements Initializable {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final QuestionGameCtrl questionGameCtrl;

  private ObservableList<String> data;
  public Timer timer = new Timer();
  private Timeline timeline;

  @FXML
  private Button startButton;

  @FXML
  private Label playersNumber;

  @FXML
  private ListView<String> waitingPlayersList;

  @FXML
  private Button homeButton;

  @FXML
  private Label label;

  @FXML
  private Button showPlayersButton;

  /**
   * Constructor for the Multi-player Waiting Room Controller.
   *
   * @param server             reference to the server the game will run on.
   * @param mainCtrl           reference to the main controller.
   * @param questionGameCtrl reference to multiple choice controller
   */
  @Inject
  public MPWaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl,
                           QuestionGameCtrl questionGameCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.questionGameCtrl = questionGameCtrl;
  }

  /**
   * Updates the list of players currently waiting.
   */
  public void updateWaitingPlayers() {
    int howManyPlayers = server.getGame().getPlayers().size();
    if (howManyPlayers != 1) {
      this.playersNumber.setText(howManyPlayers + " players waiting to start...");
    } else {
      this.playersNumber.setText("1 player waiting to start...");
    }
    List<String> currentPlayers = new ArrayList<>();
    for (Player p : server.getGame().getPlayers()) {
      currentPlayers.add(p.getName());
    }
    data = FXCollections.observableArrayList(currentPlayers);
    this.waitingPlayersList.setItems(data);
  }

  /**
   * Method that checks whether the multiplayer game can be started.
   *
   * @return a boolean
   */
  public boolean checkPlayerNo() {
    return (server.getGame().getPlayers().size() > 1);
  }

  /**
   * Starts the game in multi-player mode.
   */
  public void startMultiPlayer() {
    mainCtrl.showMoreExpensive(GameEntity.Type.MULTIPLAYER);
    questionGameCtrl.timerStart();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
      updateWaitingPlayers();
      if (checkPlayerNo()) {
        startButton.setDisable(false);
        startButton.setStyle("-fx-background-color: #11AD31");
      } else {
        startButton.setDisable(true);
        startButton.setStyle("-fx-background-color: #B3B3B3");
      }
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  /**
   * Method that starts the timeline and disables the 'showPlayersButton'.
   */
  public void startTimeline() {
    timeline.play();
    showPlayersButton.setDisable(true);
    showPlayersButton.setVisible(false);
  }

  /**
   * Have the ability to leave a multiplayer lobby.
   */
  public void goHome() {
    List<Player> players = this.server.getGame().getPlayers();
    players.remove(server.getPlayer());
    this.server.updatePlayer(players);
    mainCtrl.showChooseScreen();
  }
}

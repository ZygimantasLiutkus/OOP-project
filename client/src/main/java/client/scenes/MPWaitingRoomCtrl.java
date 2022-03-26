package client.scenes;

import client.utils.ServerUtils;
import commons.GameEntity;
import commons.Player;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javax.inject.Inject;

/**
 * Controller for the Single-player WaitingRoom scene.
 */
public class MPWaitingRoomCtrl implements Initializable {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final MultipleChoiceCtrl multipleChoiceCtrl;

  private ObservableList<String> data;

  @FXML
  private Button startButton;

  @FXML
  private Label noOfPlayers;

  @FXML
  private ListView<String> waitingPlayersList;

  @FXML
  private Button homeButton;

  /**
   * Constructor for the Multi-player Waiting Room Controller.
   *
   * @param server reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   * @param multipleChoiceCtrl reference to multiple choice controller
   */
  @Inject
  public MPWaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl,
                           MultipleChoiceCtrl multipleChoiceCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multipleChoiceCtrl = multipleChoiceCtrl;

    new Timer().scheduleAtFixedRate(new TimerTask() {
      /**
       * Run method that gets executed once every second.
       */
      @Override
      public void run() {
        updateWaitingPlayers();
        if (checkPlayerNo()) {
          startButton.setDisable(false);
          startButton.setStyle("-fx-background-color: #11AD31");
        } else {
          startButton.setDisable(true);
          startButton.setStyle("-fx-background-color: #B3B3B3");
        }
      }
    }, 0, 1000);
  }

  /**
   * Updates the list of players currently waiting.
   */
  public void updateWaitingPlayers() {
    int howManyPlayers = server.getGame().getPlayers().size();
    if (howManyPlayers != 1) {
      noOfPlayers.setText(howManyPlayers + "players waiting to start...");
    } else {
      noOfPlayers.setText("1 player waiting to start...");
    }
    List<String> currentPlayers = new ArrayList<>();
    for (Player p : server.getGame().getPlayers()) {
      currentPlayers.add(p.getName());
    }
    data = FXCollections.observableArrayList(currentPlayers);
    waitingPlayersList.setItems(data);
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
    multipleChoiceCtrl.timerStart();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    waitingPlayersList.setItems(data);
  }
}

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

/**
 * A template controller for the GameScreen scene
 */
public class GameCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label questionLabel;

    @FXML
    private ImageView questionImage;

    @FXML
    private Button answer1;

    @FXML
    private Button answer2;

    @FXML
    private Button answer3;

    @FXML
    private Label playerPoints;

    @FXML
    private Label addPoints;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label timeCounter;

    /**
     * Constructor for GameCtrl
     * @param server reference to the server the game will run on
     * @param mainCtrl reference to the main controller
     */
    @Inject
    public GameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }



}

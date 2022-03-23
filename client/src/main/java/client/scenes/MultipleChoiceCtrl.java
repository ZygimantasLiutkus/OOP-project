package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * A template controller for the MultipleChoiceScreen scene.
 */
public class MultipleChoiceCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  public Question question;
  public Random random = new Random();
  public Map<Integer, Activity> mapButtons;
  private GameEntity.Type type;
  private int startTime = 10;
  private int questionNum = 0;
  private double progress = 1;
  private Timeline timeline;
  private Timeline timeCount;
  public GameEntity dummyGameStarted = new GameEntity("STARTED");
  public GameEntity dummyGameFinished = new GameEntity("FINISHED");
  //placeholder
  private Activity test = new Activity("1", "answer2", 10, "test");
  @FXML
  private Label questionLabel;

  @FXML
  private ImageView questionImage1;

  @FXML
  private ImageView questionImage2;

  @FXML
  private ImageView questionImage3;

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

  @FXML
  private Label questionNo;

  @FXML
  private VBox jokerEl;

  @FXML
  private FlowPane emojiPane;

  /**
   * Constructor for MultipleChoiceCtrl.
   *
   * @param server   reference to the server the game will run on
   * @param mainCtrl reference to the main controller
   */
  @Inject
  public MultipleChoiceCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  public void setType(GameEntity.Type type) {
    this.type = type;
  }

  /**
   * Method to reset the selected answer by setting it to 0.
   */
  public void setSelectedAnswer0() {
    server.getPlayer().setSelectedAnswer("0");
  }

  /**
   * Method to set the selected answer to the first answer.
   */
  public void setSelectedAnswer1() {
    String answer = answer1.getText();
    server.getPlayer().setSelectedAnswer(answer);
    revealAnswer();
  }

  /**
   * Method to set the selected answer to the second answer.
   */
  public void setSelectedAnswer2() {
    String answer = answer2.getText();
    server.getPlayer().setSelectedAnswer(answer);
    revealAnswer();
  }

  /**
   * Method to set the selected answer to the third answer.
   */
  public void setSelectedAnswer3() {
    String answer = answer3.getText();
    server.getPlayer().setSelectedAnswer(answer);
    revealAnswer();
  }

  /**
   * Resets the timer.
   */
  public void resetTimer() {
    progress = 1;
    progressBar.setProgress(progress);
    progressBar.setStyle("-fx-accent: #008057");
    timeCounter.setText("10 s");
    startTime = 10;
  }

  /**
   * Starts the timer.
   */
  public void timerStart() {
    server.changeStatus(server.getIdGame(), dummyGameStarted);
    nextQuestionSingle();
    timeCounter.setVisible(true);
    timeline = new Timeline();
    timeline.setCycleCount(1000);
    timeline.setAutoReverse(false);
    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10),
        new EventHandler<>() {
          /**
           * {@inheritDoc}
           */
          @Override
          public void handle(ActionEvent event) {
            if (progress >= 0.001) {
              progressBar.setProgress(progress);
              progress -= 0.001;
            }
          }
        }));

    timeCount = new Timeline(
        new KeyFrame(Duration.seconds(1), e -> {
          startTime--;
          timeCounter.setText(startTime + " s");
          if (startTime <= 3) {
            progressBar.setStyle("-fx-accent: red");
          }
        })
    );
    timeCount.setCycleCount(10);
    timeline.play();
    timeCount.play();
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      emojiPane.setVisible(false);
    }
    timeline.setOnFinished(e -> revealAnswer());
  }

  /**
   * Reveals the correct answer to the players.
   */
  public void revealAnswer() {
    answer1.setDisable(true);
    answer2.setDisable(true);
    answer3.setDisable(true);

    int time = startTime;

    jokerEl.setVisible(false);

    boolean answerCorrectness = false;

    if (question.getText().contains("How big is the consumption per hour for this activity?")) {
      answerCorrectness = computeAnswerChoice();
    }
    if (question.getText().equals("Which is more expensive?")) {
      answerCorrectness = computeAnswerExpensive();
    }

    timeline.stop();
    timeCount.stop();
    timeCounter.setVisible(false);
    resetTimer();

    if (!answerCorrectness) {
      addPoints.setText("+0");
    } else {
      int points = 10 * (time + 1);
      if (time == 10) {
        points -= 10;
      }
      server.getPlayer().setScore(server.getPlayer().getScore() + points);
      addPoints.setText("+" + Integer.toString(points));
    }

    addPoints.setVisible(true);

    Timeline cooldown = new Timeline();
    cooldown.getKeyFrames().add(new KeyFrame(Duration.millis(3000), e -> {
    }));
    cooldown.play();
    cooldown.setOnFinished(e -> {
      cooldownAnswer();
    });
  }

  /**
   * Changes buttons' colour according to the computed answer.
   *
   * @return correct/incorrect selected answer
   */
  public boolean computeAnswerChoice() {
    int answer = question.getActivities().get(0).getConsumption_in_wh();
    if (mapButtons.get(1).getConsumption_in_wh() != answer) {
      answer1.setStyle("-fx-background-color: E50C0C");
    }
    if (mapButtons.get(2).getConsumption_in_wh() != answer) {
      answer2.setStyle("-fx-background-color: E50C0C");
    }
    if (mapButtons.get(3).getConsumption_in_wh() != answer) {
      answer3.setStyle("-fx-background-color: E50C0C");
    }
    return server.getPlayer().getSelectedAnswer().equals(Integer.toString(answer) + " wh");
  }

  /**
   * Changes buttons' colour according to the computed answer.
   *
   * @return correct/incorrect selected answer
   */
  public boolean computeAnswerExpensive() {
    int max = 0;
    int imax = 1;
    for (int i = 0; i < 3; i++) {
      if (question.getActivities().get(i).getConsumption_in_wh() >= max) {
        max = question.getActivities().get(i).getConsumption_in_wh();
        imax = i;
      }
    }
    if (mapButtons.get(1).getConsumption_in_wh() != max) {
      answer1.setStyle("-fx-background-color: E50C0C");
    }
    if (mapButtons.get(2).getConsumption_in_wh() != max) {
      answer2.setStyle("-fx-background-color: E50C0C");
    }
    if (mapButtons.get(3).getConsumption_in_wh() != max) {
      answer3.setStyle("-fx-background-color: E50C0C");
    }
    return server.getPlayer().getSelectedAnswer()
        .equals(question.getActivities().get(imax).getTitle());
  }

  /**
   * Checks if the game type is single player and does the associated methods.
   */
  public void cooldownAnswer() {
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      if (questionNum < 20) {
        resetTimer();
        timerStart();
      } else {
        String name = server.getPlayer().getName();
        int points = server.getPlayer().getScore();
        LeaderboardEntry entry = new LeaderboardEntry(name, points);
        entry = server.addLeaderboardEntry(entry);
        mainCtrl.showSPLeaderboard(entry);
      }
    } else {
      if (questionNum < 20) {
        timerStart();
        if (type.equals("SINGLEPLAYER")) {
          nextQuestionSingle();
        } else {
          nextQuestionMultiple();
        }
      } else {
        mainCtrl.showMPLeaderboard();
      }
    }
  }

  /**
   * Makes the client screen ready for the new question. FOR SINGLE PLAYER ONLY
   */
  public void nextQuestionSingle() {
    setText();
    resetTimer();
    jokerEl.setVisible(true);
    questionNum++;
    addPoints.setVisible(false);
    questionNo.setText(questionNum + "/20");
    answer1.setDisable(false);
    answer1.setStyle("-fx-background-color: #11AD31");
    answer2.setDisable(false);
    answer2.setStyle("-fx-background-color: #11AD31");
    answer3.setDisable(false);
    answer3.setStyle("-fx-background-color: #11AD31");
    server.resetAnswer();
    playerPoints.setText(Integer.toString(server.getPlayer().getScore()) + " points");
  }

  /**
   * Makes the client ready for the new question. FOR MULTIPLAYER ONLY
   */
  public void nextQuestionMultiple() {
    revealAnswer();
  }

  /**
   * Getter for answer1.
   *
   * @return value of answer1
   */
  public String getAnswer1() {
    return answer1.getText();
  }

  /**
   * Getter for answer2.
   *
   * @return value of answer2
   */
  public String getAnswer2() {
    return answer2.getText();
  }

  /**
   * Getter for answer3.
   *
   * @return value of answer3
   */
  public String getAnswer3() {
    return answer3.getText();
  }

  /**
   * Getter for the player points .
   *
   * @return value of the player points
   */
  public String getPlayerPoints() {
    return playerPoints.getText();
  }

  /**
   * Getter for progressbar.
   *
   * @return value of progressbar
   */
  public double getProgressBar() {
    return progressBar.getProgress();
  }

  /**
   * Getter for timer counter.
   *
   * @return value of the timer counter
   */
  public int getTimeCounter() {
    String[] time = timeCounter.getText().split(" s");
    return Integer.parseInt(time[0]);
  }

  /**
   * Set the texts of the texts fields by question data.
   */
  public void setText() {
    setQuestion(server.getQuestion(server.getIdGame(),
        String.valueOf(questionNum + 1)));
    setMapButtons();
    if (this.question.getText().equals("Which is more expensive?")) {
      prepareMoreExpensive();
    } else if (this.question.getText()
        .equals("How big is the consumption per hour for this activity?")) {
      prepareMultipleChoice();
    }
  }

  /**
   * Prepare the screen for a more expensive question.
   */
  public void prepareMoreExpensive() {
    this.questionLabel.setText(question.getText());
    this.answer1.setText(mapButtons.get(1).getTitle());
    try {
      this.questionImage1.setImage(
          (new Image("client/images/" + mapButtons.get(1).getImage_path())));
    } catch (IllegalArgumentException e) {
      this.questionImage1.setImage(new Image("client/images/defaultImage.png"));
    }
    try {
      this.questionImage2.setImage(
          (new Image("client/images/" + mapButtons.get(2).getImage_path())));
    } catch (IllegalArgumentException e) {
      this.questionImage2.setImage(new Image("client/images/flatFaceEmoji.png"));
    }
    try {
      this.questionImage3.setImage(
          (new Image("client/images/" + mapButtons.get(3).getImage_path())));
    } catch (IllegalArgumentException e) {
      this.questionImage3.setImage(new Image("client/images/defaultImage.png"));
    }
    this.answer2.setText(mapButtons.get(2).getTitle());
    this.answer3.setText(mapButtons.get(3).getTitle());
    this.questionImage1.setVisible(true);
    this.questionImage2.setVisible(true);
    this.questionImage3.setVisible(true);
  }

  /**
   * Prepare screen for a multiple choice question.
   */
  public void prepareMultipleChoice() {
    this.questionLabel.setText(
        question.getText() + "\n" + question.getActivities().get(0).getTitle());
    this.answer1.setText(String.valueOf(mapButtons.get(1).getConsumption_in_wh()) + " wh");
    this.answer2.setText(String.valueOf(mapButtons.get(2).getConsumption_in_wh()) + " wh");
    this.answer3.setText(String.valueOf(mapButtons.get(3).getConsumption_in_wh()) + " wh");
    try {
      this.questionImage2.setImage(
          (new Image("client/images/" + question.getActivities().get(0).getImage_path())));
    } catch (IllegalArgumentException e) {
      this.questionImage2.setImage(new Image("client/images/defaultImage.png"));
    }
    this.questionImage1.setVisible(false);
    this.questionImage3.setVisible(false);
  }

  /**
   * Map a button with an activity to ease feedback process.
   */
  public void setMapButtons() {
    mapButtons = new HashMap<>();
    for (int i = 0; i < 3; i++) {
      int index = random.nextInt(4);
      while (mapButtons.containsKey(index) || index == 0) {
        index = random.nextInt(4);
      }
      mapButtons.put(index, question.getActivities().get(i));
    }
  }

  /**
   * Setter for the question.
   *
   * @param q the question got from the server
   */
  public void setQuestion(Question q) {
    this.question = q;
  }
}

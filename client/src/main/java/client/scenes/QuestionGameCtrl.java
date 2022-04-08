package client.scenes;

import client.utils.ServerUtils;
import client.utils.TimerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.GameEntity;
import commons.LeaderboardEntry;
import commons.Message;
import commons.Player;
import commons.Question;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


/**
 * A template controller for the MultipleChoiceScreen scene.
 */
public class QuestionGameCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  public Question question;
  public Random random = new Random();
  public Map<Integer, Activity> mapButtons;
  public GameEntity dummyGameStarted = new GameEntity("STARTED");
  public GameEntity dummyGameFinished = new GameEntity("FINISHED");
  public GameEntity dummyGameAborted = new GameEntity("ABORTED");
  public boolean pointsUsed = false;
  public boolean answerUsed = false;
  public boolean validateJoker = true;
  public List<Player> players;
  private GameEntity.Type type;
  private int startTime = 15;
  private int questionNum = 0;
  private double progress = 1;
  private Timeline timeline;
  private Timeline timeCount;
  private Timeline cooldown;
  private Timeline interTime;
  private int answerTime = 0;

  @FXML
  private ImageView homeButton;

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

  @FXML
  private FlowPane emojiButtonPane;

  @FXML
  private TextField textArea;

  @FXML
  private Button submitButton;

  @FXML
  private Label answerText;

  @FXML
  private Label textPrompt;

  @FXML
  private Label validator;

  @FXML
  private ListView<ImageView> messageEmojiList;

  @FXML
  private ListView<String> messageNameList;

  @FXML
  private Label chatLabel;

  @FXML
  private Button jokerPoints;

  @FXML
  private Button jokerAnswer;

  @FXML
  private Button jokerTime;

  /**
   * Constructor for QuestionGameCtrl.
   *
   * @param server   reference to the server the game will run on
   * @param timers   reference to the timer utils that sets up the timers
   * @param mainCtrl reference to the main controller
   */
  @Inject
  public QuestionGameCtrl(ServerUtils server, TimerUtils timers, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.timeCount = timers.setupCounter(this);
    this.timeline = timers.setupTimeline(this);
    this.cooldown = timers.setupCooldown(this);
    this.interTime = timers.intermediateTable(this, mainCtrl);
  }

  /**
   * Method to "disconnect" from a game.
   * It also stops a game from running if game type is sp.
   * (For now it works best for sp as there is no notification of disconnecting)
   */
  public void goHomeScreen() {
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      cooldown.stop();
      questionNum = 20;
      timeline.stop();
      timeCount.stop();
      gracefulExit();
      server.changeStatus(dummyGameAborted);
    }
    if (type.equals(GameEntity.Type.MULTIPLAYER)) {
      disconnect();
    }
    mainCtrl.showChooseScreen();
  }

  /**
   * Looks for an ENTER key press.
   *
   * @param e the events of typing.
   */
  public void keyPressed(KeyEvent e) {
    validator.setVisible(e.getCode().isLetterKey());
    this.textPrompt.setVisible(false);
    if (e.getCode().equals(KeyCode.ENTER)) {
      sendAnswer();
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
   * The method linked to the submit button.
   */
  public void sendAnswer() {
    String answer = String.valueOf(question.getActivities().get(0).getConsumption_in_wh());
    this.textArea.setEditable(false);
    server.getPlayer().setSelectedAnswer(answer);
    this.submitButton.setDisable(true);
    this.submitButton.setVisible(false);
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      revealAnswer();
    }
  }

  /**
   * Method to flag the use of a joker.
   */
  public void usePointsJoker() {
    pointsUsed = true;
    jokerPoints.setDisable(true);
    jokerPoints.setVisible(false);
  }

  /**
   * Method to flag the use of a joker.
   */
  public void useTimer() {
    jokerTime.setDisable(true);
    jokerTime.setVisible(false);
    sendJokerTime();
  }

  /**
   * Method to flag the use of a joker.
   */
  public void useAnswerJoker() {
    answerUsed = true;
    jokerAnswer.setVisible(false);
    jokerAnswer.setDisable(true);
    if (question.getText().contains("Which is more expensive?")) {
      discardExpensive();
    } else {
      discardMultiple();
    }
  }

  /**
   * Method to discard an answer for more expensive type.
   */
  private void discardExpensive() {
    int answer = 0;
    for (int i = 0; i < 3; i++) {
      if (mapButtons.get(i + 1).getConsumption_in_wh() > answer) {
        answer = mapButtons.get(i + 1).getConsumption_in_wh();
      }
    }
    int index = random.nextInt(3);
    while (mapButtons.get(index + 1).getConsumption_in_wh() == answer) {
      index = random.nextInt(3);
    }
    deleteJoker(index);
  }

  /**
   * Method to discard an answer for multiple choice type.
   */
  private void discardMultiple() {
    int answer = question.getActivities().get(0).getConsumption_in_wh();
    int index = random.nextInt(3);
    while (mapButtons.get(index + 1).getConsumption_in_wh() == answer) {
      index = random.nextInt(3);
    }
    deleteJoker(index);
  }

  /**
   * Deletes an answer button from the scene.
   *
   * @param index the index of the button to be deleted
   */
  public void deleteJoker(int index) {
    switch (index) {
      case 0:
        answer1.setVisible(false);
        answer1.setDisable(true);
        break;
      case 1:
        answer2.setVisible(false);
        answer2.setDisable(true);
        break;
      case 2:
        answer3.setVisible(false);
        answer3.setDisable(true);
        break;
      default:
        break;
    }
  }

  /**
   * Setter for the game type.
   *
   * @param type the type of game it was created
   */
  public void setType(GameEntity.Type type) {
    this.type = type;
  }

  /**
   * Method to set the selected answer to the first answer.
   */
  public void setSelectedAnswer1() {
    this.answerTime = getTimeCounter();
    String answer = answer1.getText();
    server.getPlayer().setSelectedAnswer(answer);
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      revealAnswer();
    } else {
      answerMP("answer1");
    }
  }

  /**
   * Method to set the selected answer to the second answer.
   */
  public void setSelectedAnswer2() {
    this.answerTime = getTimeCounter();
    String answer = answer2.getText();
    server.getPlayer().setSelectedAnswer(answer);
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      revealAnswer();
    } else {
      answerMP("answer2");
    }
  }

  /**
   * Method to set the selected answer to the third answer.
   */
  public void setSelectedAnswer3() {
    this.answerTime = getTimeCounter();
    String answer = answer3.getText();
    server.getPlayer().setSelectedAnswer(answer);
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      revealAnswer();
    } else {
      answerMP("answer3");
    }
  }

  /**
   * Marks the selected answer for multiplayer games.
   *
   * @param bNum number of button pressed.
   */
  public void answerMP(String bNum) {
    switch (bNum) {
      case "answer1":
        answer1.setDisable(true);
        answer2.setDisable(true);
        answer3.setDisable(true);
        answer1.setStyle("-fx-border-color: EFDB22;\n"
            + "-fx-border-insets: 1;\n"
            + "-fx-border-width: 4;\n"
            + "-fx-border-style: solid;\n");
        answer3.setStyle("-fx-opacity: 0.5");
        answer2.setStyle("-fx-opacity: 0.5");
        break;
      case "answer2":
        answer1.setDisable(true);
        answer2.setDisable(true);
        answer3.setDisable(true);
        answer1.setStyle("-fx-opacity: 0.5");
        answer3.setStyle("-fx-opacity: 0.5");
        answer2.setStyle("-fx-border-color: EFDB22;\n"
            + "-fx-border-insets: 1;\n"
            + "-fx-border-width: 4;\n"
            + "-fx-border-style: solid;\n");
        break;
      case "answer3":
        answer1.setDisable(true);
        answer2.setDisable(true);
        answer3.setDisable(true);
        answer1.setStyle("-fx-opacity: 0.5");
        answer2.setStyle("-fx-opacity: 0.5");
        answer3.setStyle("-fx-border-color: EFDB22;\n"
            + "-fx-border-insets: 1;\n"
            + "-fx-border-width: 4;\n"
            + "-fx-border-style: solid;\n");
        break;
      default:
        break;
    }
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
   * Gets the progress.
   *
   * @return the progress
   */
  public double getProgress() {
    return progress;
  }

  /**
   * Sets the progress.
   *
   * @param progress the progress to set
   */
  public void setProgress(double progress) {
    this.progress = progress;
    progressBar.setProgress(progress);
  }

  /**
   * Gets the startTime.
   *
   * @return the startTime
   */
  public int getStartTime() {
    return startTime;
  }

  /**
   * Updates the counter.
   *
   * @param time the time to set the counter to
   */
  public void updateCounter(int time) {
    this.startTime = time;
    timeCounter.setText(startTime + " s");
    if (startTime <= 3) {
      progressBar.setStyle("-fx-accent: red");
    }
    if (startTime < 0) {
      timeRunOut();
    }
  }

  /**
   * Method that disables answer buttons when the time runs out.
   */
  public void timeRunOut() {
    this.submitButton.setVisible(false);
    this.textArea.setEditable(false);
    String answer = server.getPlayer().getSelectedAnswer();
    timeCounter.setVisible(false);
    if (!answer.equals(answer1.getText())) {
      answer1.setDisable(true);
      answer1.setStyle("-fx-opacity: 0.5");
    }
    if (!answer.equals(answer2.getText())) {
      answer2.setDisable(true);
      answer2.setStyle("-fx-opacity: 0.5");
    }
    if (!answer.equals(answer3.getText())) {
      answer3.setDisable(true);
      answer3.setStyle("-fx-opacity: 0.5");
    }
  }

  /**
   * Starts the game.
   */
  public void startGame() {
    server.changeStatus(dummyGameStarted);
    if (this.questionNum == 20) {
      this.questionNum = 0;
      answerUsed = false;
      pointsUsed = false;
      validateJoker = true;
    }
    if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
      emojiPane.setVisible(false);
      jokerTime.setVisible(false);
      emojiButtonPane.setVisible(false);
      messageEmojiList.setVisible(false);
      messageNameList.setVisible(false);
      chatLabel.setVisible(false);
    }

    nextQuestion();
  }

  /**
   * Starts the timer.
   */
  public void startTimer() {
    timeCounter.setVisible(true);
    timeline.playFromStart();
    timeCount.playFromStart();
  }

  /**
   * Resets the timer.
   */
  public void resetTimer() {
    progress = 1;
    progressBar.setProgress(progress);
    progressBar.setStyle("-fx-accent: #008057");
    timeCounter.setText("15 s");
    startTime = 15;
  }

  /**
   * Reveals the correct answer to the players.
   */
  public void revealAnswer() {
    answer1.setDisable(true);
    answer2.setDisable(true);
    answer3.setDisable(true);

    answer1.setStyle("-fx-opacity: 1");
    answer2.setStyle("-fx-opacity: 1");
    answer3.setStyle("-fx-opacity: 1");

    boolean answerCorrectness = false;

    if (question.getText().equals("How much do you think this activity consumes per hour?")) {
      this.textPrompt.setVisible(false);
      answerCorrectness = computeAnswerEstimation();
    }

    if (question.getText().equals("How big is the consumption per hour for this activity?")) {
      answerCorrectness = computeAnswerChoice();
    }
    if (question.getText().equals("Which is more expensive?")) {
      answerCorrectness = computeAnswerExpensive();
    }

    timeline.stop();
    timeCount.stop();
    timeCounter.setVisible(false);
    resetTimer();
    int points;
    if (!answerCorrectness) {
      addPoints.setText("+0");

    } else {
      //The points are calculated depending on how close you were to the actual answer.
      if (question.getText().equals("How much do you think this activity consumes per hour?")) {
        int realAnswer = question.getActivities().get(0).getConsumption_in_wh();
        double percentageOff =
            Math.abs((double) Integer.parseInt(textArea.getText()) - realAnswer) / realAnswer;
        points = (int) (150 * (1 - percentageOff / 0.3));
      } else {

        points = 10 * (answerTime + 1);
        if (answerTime == 15) {
          points -= 10;
        }
      }

      if (pointsUsed && validateJoker) {
        points *= 2;
        validateJoker = false;
      }
      server.updateScore(points);
      addPoints.setText("+" + points);
    }

    if (pointsUsed) {
      validateJoker = false;
    }
    //If someone didn't submit anything and just pressed the button, the answer is automatically 0.
    if (question.getText().equals("How much do you think this activity consumes per hour?")) {
      this.validator.setVisible(false);
      try {
        Long.parseLong(textArea.getText());
      } catch (NumberFormatException e) {
        textArea.setText("0");
      }
      if (textArea.getText().equals("")) {
        textArea.setText("0");
      }
      //If the question type was submission, a label which shows the exact answer should appear.
      answerText.setText(
          "The correct answer was: " + question.getActivities().get(0).getConsumption_in_wh()
              + ".\nYour answer was: " + textArea.getText());
      answerText.setVisible(true);
    }
    addPoints.setVisible(true);

    cooldown.playFromStart();
  }

  /**
   * Checks if the game type is single player and does the associated methods.
   */
  public void cooldownAnswer() {
    if (questionNum == 10 && type.equals(GameEntity.Type.MULTIPLAYER)) {
      server.send("/app/messages", "intermediate");
    } else if (questionNum < 20) {
      nextQuestion();
    } else {
      gracefulExit();
      mainCtrl.showButtons();
      LeaderboardEntry entry = lbEntry();
      server.changeStatus(dummyGameFinished);
      if (type.equals(GameEntity.Type.SINGLEPLAYER)) {
        entry = server.addLeaderboardEntry(entry);
        mainCtrl.showSPLeaderboard(entry);
      } else {
        server.send("/app/messages", "finished");
        mainCtrl.showMPLeaderboard(entry);
      }
    }
  }

  /**
   * Method that shows the intermediate table.
   */
  public void showIntermediateTable() {
    server.setPlayersFinished(server.getPlayersFinished() + 1);
    LeaderboardEntry entry = lbEntry();

    mainCtrl.showMPIntermediate(entry);
    mainCtrl.setMPLeaderboard();
    if (server.getPlayersFinished() >= server.getGame().getPlayers().size()) {
      interTime.play();
      server.setPlayersFinished(0);
    }
  }

  /**
   * Method that creates a leaderboard entry.
   *
   * @return a new leaderboard entry.
   */
  public LeaderboardEntry lbEntry() {
    String name = server.getPlayer().getName();
    int points = server.getPlayer().getScore();
    return new LeaderboardEntry(name, points);
  }

  /**
   * Method that resets the jokers vision.
   */
  private void resetJokers() {
    if (!answerUsed) {
      if (this.question.getText()
          .equals("How much do you think this activity consumes per hour?")) {
        jokerAnswer.setDisable(true);
        jokerAnswer.setVisible(false);
      } else {
        jokerAnswer.setVisible(true);
        jokerAnswer.setDisable(false);
      }
    }
    if (pointsUsed) {
      jokerPoints.setDisable(true);
      jokerPoints.setVisible(false);
    } else {
      jokerPoints.setVisible(true);
      jokerPoints.setDisable(false);
    }
  }

  /**
   * Makes the client screen ready for the new question. FOR SINGLE PLAYER ONLY
   */
  public void nextQuestion() {
    setText();
    // pointsUsed = false;
    resetTimer();
    resetJokers();
    questionNum++;
    addPoints.setVisible(false);
    questionNo.setText(questionNum + "/20");
    if (!this.question.getText().equals("How much do you think this activity consumes per hour?")) {
      answer1.setDisable(false);
      answer1.setStyle("-fx-background-color: #11AD31");
      answer1.setVisible(true);
      answer2.setDisable(false);
      answer2.setStyle("-fx-background-color: #11AD31");
      answer2.setVisible(true);
      answer3.setDisable(false);
      answer3.setStyle("-fx-background-color: #11AD31");
      answer3.setVisible(true);
    }
    textArea.setText("");
    this.validator.setVisible(false);
    this.validator.setDisable(true);
    server.resetAnswer();
    playerPoints.setText(server.getPlayer().getScore() + " points");
    startTimer();
  }

  /**
   * Set the texts of the texts fields by question data.
   */
  public void setText() {
    setQuestion(server.getQuestion(String.valueOf(questionNum + 1)));
    if (!this.question.getText().equals("How much do you think this activity consumes per hour?")) {
      submitButton.setDisable(true);
      submitButton.setVisible(false);
      textArea.setDisable(true);
      textArea.setVisible(false);
      answerText.setVisible(false);
      textPrompt.setVisible(false);
      textPrompt.setDisable(true);
      setMapButtons();
      if (this.question.getText().equals("Which is more expensive?")) {
        prepareMoreExpensive();
      } else if (this.question.getText()
          .equals("How big is the consumption per hour for this activity?")) {
        prepareMultipleChoice();
      }
    } else {
      prepareEstimation();
    }
  }

  /**
   * Method to prepare the screen for the estimation question type.
   */
  public void prepareEstimation() {
    this.textPrompt.setVisible(true);
    this.textPrompt.setDisable(true);
    this.submitButton.setDisable(false);
    this.submitButton.setVisible(true);
    this.textArea.setEditable(true);
    this.textArea.setDisable(false);
    this.textArea.setVisible(true);
    this.answerText.setVisible(false);
    this.answer1.setDisable(true);
    this.answer1.setVisible(false);
    this.answer2.setDisable(true);
    this.answer2.setVisible(false);
    this.answer3.setDisable(true);
    this.answer3.setVisible(false);
    this.questionImage1.setVisible(false);
    this.questionImage3.setVisible(false);
    this.questionLabel.setText(
        question.getText() + "\n" + question.getActivities().get(0).getTitle());
    this.questionImage2.setImage(server.getImage(question.getActivities().get(0).getImage_path()));
  }

  /**
   * Prepare the screen for a more expensive question.
   */
  public void prepareMoreExpensive() {
    this.questionLabel.setText(question.getText());
    this.answer1.setText(mapButtons.get(1).getTitle());

    this.questionImage1.setImage(server.getImage(mapButtons.get(1).getImage_path()));
    this.questionImage2.setImage(server.getImage(mapButtons.get(2).getImage_path()));
    this.questionImage3.setImage(server.getImage(mapButtons.get(3).getImage_path()));

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
    this.answer1.setText(mapButtons.get(1).getConsumption_in_wh() + " wh");
    this.answer2.setText(mapButtons.get(2).getConsumption_in_wh() + " wh");
    this.answer3.setText(mapButtons.get(3).getConsumption_in_wh() + " wh");
    this.questionImage2.setImage(server.getImage(question.getActivities().get(0).getImage_path()));
    this.questionImage1.setVisible(false);
    this.questionImage3.setVisible(false);
  }

  /**
   * Method to calculate the exact correct answer.
   *
   * @return a boolean that decides whether you receive points or not
   */
  public boolean computeAnswerEstimation() {
    try {
      Integer.parseInt(this.textArea.getText());
    } catch (NumberFormatException nfe) {
      return false;
    }
    int answer = question.getActivities().get(0).getConsumption_in_wh();
    int bound = 30 * answer / 100;
    return Integer.parseInt(this.textArea.getText()) >= (answer - bound)
        && Integer.parseInt(this.textArea.getText()) <= (answer + bound);
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
    return server.getPlayer().getSelectedAnswer().equals(answer + " wh");
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
   * Method that sets up communication for the client.
   */
  public void startCommunication() {
    server.connect();
    server.registerForMessages("/topic/messages/" + server.getPlayer().getGameId(), message -> {
      if (message.getText().equals("time")) {
        Platform.runLater(() -> {
          timeJoker(message);
        });
      } else if (message.getText().equals("intermediate")) {
        Platform.runLater(this::showIntermediateTable);
      } else {
        Platform.runLater(() -> {
          showMessage(message);
        });
      }
    });
  }

  /**
   * Method that sends the server a message with the player's name and the laughing emoji.
   */
  public void sendEmojiLaughing() {
    server.send("/app/messages", "laughing");
  }

  /**
   * Method that sends the server a message with the player's name and the ok emoji.
   */
  public void sendEmojiOk() {
    server.send("/app/messages", "okHand");
  }

  /**
   * Method that sends the server a message with the player's name and the thumbsUp emoji.
   */
  public void sendEmojiThumbsUp() {
    server.send("/app/messages", "thumbsUp");
  }

  /**
   * Method that sends the server a message with the player's name and the flatFace emoji.
   */
  public void sendEmojiFlatFace() {
    server.send("/app/messages", "flatFace");
  }

  /**
   * Method that sends the server a message with the player's name and the angry emoji.
   */
  public void sendEmojiAngry() {
    server.send("/app/messages", "angry");
  }

  /**
   * Method that adds an emoji message to the chat.
   *
   * @param message the message to be added
   */
  public void showMessage(Message message) {

    if (messageEmojiList.getItems().size() > 6) {
      for (int i = messageEmojiList.getItems().size() - 1; i > 5; i--) {
        messageEmojiList.getItems().remove(i);
        messageNameList.getItems().remove(i);
      }
    }
    if (message.getText().equals("disconnected")) {
      server.setPlayersFinished(server.getPlayersFinished() + 1);
      if (server.getPlayersFinished() >= server.getGame().getPlayers().size()) {
        mainCtrl.setMPLeaderboard();
      }
      messageEmojiList.getItems()
          .add(0, new ImageView(new Image("client/images/left.png")));
      messageNameList.getItems().add(0, message.getPlayerName());
      players.removeIf(p -> p.getName().equals(message.getPlayerName()));
    } else if (message.getText().equals("finished")) {
      server.setPlayersFinished(server.getPlayersFinished() + 1);
      if (server.getPlayersFinished() >= server.getGame().getPlayers().size()) {
        mainCtrl.setMPLeaderboard();
      }
    } else {
      messageEmojiList.getItems()
          .add(0,
              new ImageView(new Image("client/images/" + message.getText() + "Emoji.png")));
      messageEmojiList.getItems().get(0).setFitWidth(30);
      messageEmojiList.getItems().get(0).setFitHeight(30);

      messageNameList.getItems().add(0, message.getPlayerName());
    }
  }

  /**
   * Method that disconnects a player from a multiplayer game and notifies the others.
   */
  public void disconnect() {
    server.send("/app/messages", "disconnected");
    server.session.disconnect();

    gracefulExit();

    cooldown.stop();
    timeline.stop();
    timeCount.stop();
    interTime.stop();
    questionNum = 20;

    if (players.size() <= 1) {
      server.changeStatus(dummyGameAborted);
    }
  }

  /**
   * Sends a message with the player's name and an indication that the time joker has been used.
   */
  public void sendJokerTime() {
    server.send("/app/messages", "time");
  }

  /**
   * Reduces the time for other players.
   *
   * @param message the message that was sent by the player that used the joker.
   */
  public void timeJoker(Message message) {
    if (!server.getPlayer().getName().equals(message.getPlayerName()) && getTimeCounter() > 5) {
      int newTime = startTime / 2;
      progress /= 2;
      updateCounter(newTime);
    }

    if (messageEmojiList.getItems().size() > 6) {
      for (int i = messageEmojiList.getItems().size() - 1; i > 5; i--) {
        messageEmojiList.getItems().remove(i);
        messageNameList.getItems().remove(i);
      }
    }
    ImageView empty = new ImageView();
    messageEmojiList.getItems().add(0, empty);
    messageEmojiList.getItems().get(0).setFitWidth(30);
    messageEmojiList.getItems().get(0).setFitHeight(30);
    messageNameList.getItems().add(0, "Time joker");
  }

  /**
   * Method to make a graceful game exit by resetting the emoji chat and the jokers.
   */
  public void gracefulExit() {
    messageEmojiList.getItems().clear();
    messageNameList.getItems().clear();
    pointsUsed = false;
    answerUsed = false;
    jokerPoints.setDisable(false);
    jokerPoints.setVisible(true);
    jokerAnswer.setDisable(false);
    jokerAnswer.setVisible(true);
    jokerTime.setDisable(false);
    jokerTime.setVisible(true);

    emojiPane.setVisible(true);
    emojiButtonPane.setVisible(true);
    messageEmojiList.setVisible(true);
    messageNameList.setVisible(true);
    chatLabel.setVisible(true);
  }
}

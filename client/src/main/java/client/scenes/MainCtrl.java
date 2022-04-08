/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.utils.NextScreen;
import commons.Activity;
import commons.GameEntity;
import commons.LeaderboardEntry;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The main controller of the client application.
 */
public class MainCtrl {

  private Stage primaryStage;

  private Stage popup;

  private EntryCtrl entryCtrl;
  private Scene entry;

  private NamePopupCtrl namePopupCtrl;
  private Scene name;

  private ChooseScreenCtrl chooseScreenCtrl;
  private Scene choose;

  private QuestionGameCtrl questionGameCtrl;
  private Scene questionGame;

  private LeaderboardScreenCtrl leaderboardScreenCtrl;
  private Scene leaderboard;

  private WaitingRoomCtrl waitingRoomCtrl;
  private Scene waitingRoomSP;

  private MPWaitingRoomCtrl mpWaitingRoomCtrl;
  private Scene waitingRoomMP;

  private CountdownCtrl countdownCtrl;
  private Scene countdown;

  private ActivityOverviewCtrl activityCtrl;
  private Scene activityList;

  private ActivityPopUpCtrl activityPopUpCtrl;
  private Scene activityPopUp;

  /**
   * Initializes the main controller.
   *
   * @param primaryStage     the top level JavaFX container.
   * @param entry            a pair of the EntryScreen controller and the parent.
   * @param name             a pair of the NamePopup controller and the parent.
   * @param choose           a pair of the ChooseScreen controller and the parent.
   * @param questionGame     a pair of the QuestionGame controller and the parent.
   * @param leaderboard      a pair of the LeaderboardScreen controller and the parent.
   * @param waitingRoomSP    a pair of the sp WaitingRoomScreen controller and the parent.
   * @param waitingRoomMP    a pair of the mp WaitingRoomScreen controller and the parent.
   * @param countdown        a pair of the countdown controller and the parent.
   * @param activityOverview a pair of the activityOverview controller and the parent.
   * @param activityPopUp    a pair of the ActivityPopUp controller and the parent.
   */
  public void initialize(Stage primaryStage,
                         Pair<EntryCtrl, Parent> entry,
                         Pair<NamePopupCtrl, Parent> name, Pair<ChooseScreenCtrl, Parent> choose,
                         Pair<QuestionGameCtrl, Parent> questionGame,
                         Pair<LeaderboardScreenCtrl, Parent> leaderboard,
                         Pair<WaitingRoomCtrl, Parent> waitingRoomSP,
                         Pair<MPWaitingRoomCtrl, Parent> waitingRoomMP,
                         Pair<CountdownCtrl, Parent> countdown,
                         Pair<ActivityOverviewCtrl, Parent> activityOverview,
                         Pair<ActivityPopUpCtrl, Parent> activityPopUp) {

    this.primaryStage = primaryStage;

    this.entryCtrl = entry.getKey();
    this.entry = new Scene(entry.getValue());

    this.namePopupCtrl = name.getKey();
    this.name = new Scene(name.getValue());

    this.chooseScreenCtrl = choose.getKey();
    this.choose = new Scene(choose.getValue());

    this.questionGameCtrl = questionGame.getKey();
    this.questionGame = new Scene(questionGame.getValue());

    this.leaderboardScreenCtrl = leaderboard.getKey();
    this.leaderboard = new Scene(leaderboard.getValue());

    this.waitingRoomCtrl = waitingRoomSP.getKey();
    this.waitingRoomSP = new Scene(waitingRoomSP.getValue());

    this.mpWaitingRoomCtrl = waitingRoomMP.getKey();
    this.waitingRoomMP = new Scene(waitingRoomMP.getValue());


    this.countdownCtrl = countdown.getKey();
    this.countdown = new Scene(countdown.getValue());

    this.activityCtrl = activityOverview.getKey();
    this.activityList = new Scene(activityOverview.getValue());

    this.activityPopUpCtrl = activityPopUp.getKey();
    this.activityPopUp = new Scene(activityPopUp.getValue());

    primaryStage.setOnCloseRequest(event -> {
      event.consume();
      if (primaryStage.getScene().equals(questionGame)) {
        questionGameCtrl.goHomeScreen();
      }
      if (primaryStage.getScene().equals(this.waitingRoomMP)) {
        mpWaitingRoomCtrl.goHome();
      }
      if (primaryStage.getScene().equals(leaderboard)) {
        leaderboardScreenCtrl.home();
      }
      Platform.exit();
    });

    showEntry();
    primaryStage.show();

    this.popup = new Stage();
    this.popup.setMinWidth(500);
    this.popup.setMinHeight(280);
    this.popup.initModality(Modality.APPLICATION_MODAL);
    this.popup.initOwner(primaryStage);
  }

  /**
   * Shows the game entry screen.
   */
  public void showEntry() {
    primaryStage.setTitle("Quizzzz");
    primaryStage.setScene(entry);
    entryCtrl.animate();
    entry.setOnKeyPressed(e -> entryCtrl.keyPressed(e));
  }

  /**
   * Shows the name popup.
   *
   * @param nextScreen the screen to be shown after the name is entered
   */
  public void showNamePopup(NextScreen nextScreen) {
    popup.setTitle("Choose your name!");
    popup.setScene(name);
    name.setOnKeyPressed(e -> namePopupCtrl.keyPressed(e));
    namePopupCtrl.setNextScreen(nextScreen);
    namePopupCtrl.initializeName();
    namePopupCtrl.showErrorText(false);
    popup.show();
  }

  /**
   * Closes the name popup.
   */
  public void closeNamePopup() {
    popup.close();
  }

  /**
   * Shows the choose game style screen.
   */
  public void showChooseScreen() {
    primaryStage.setTitle("Choose the game style!");
    primaryStage.setScene(choose);
    chooseScreenCtrl.animate();
  }

  /**
   * Shows the game screen.
   *
   * @param type the type of the game
   */
  public void showQuestionGame(GameEntity.Type type) {
    primaryStage.setTitle("Quizzzz");
    questionGameCtrl.setType(type);
    primaryStage.setScene(questionGame);
  }

  /**
   * Shows countdows before game.
   *
   * @param type the type of game
   */
  public void showCountdown(GameEntity.Type type) {
    primaryStage.setTitle("Quizzzz");
    primaryStage.setScene(countdown);
    countdownCtrl.animate(type);
  }

  /**
   * Shows the global leaderboard with the entry of the current player.
   *
   * @param entry the leaderboardEntry of the current player
   */
  public void showSPLeaderboard(LeaderboardEntry entry) {
    primaryStage.setTitle("Quizzzz Leaderboard");
    leaderboardScreenCtrl.setSingleplayer(entry);
    primaryStage.setScene(leaderboard);
    leaderboardScreenCtrl.refreshTop10();
  }

  /**
   * Shows the leaderboard screen as multiplayer leaderboard.
   *
   * @param entry the leaderboardEntry of the current player
   */
  public void showMPLeaderboard(LeaderboardEntry entry) {
    primaryStage.setTitle("Match Leaderboard");
    leaderboardScreenCtrl.setScoreLabel("Generating scores...");
    leaderboardScreenCtrl.setMultiplayer(entry);
    primaryStage.setScene(leaderboard);
  }

  /**
   * Shows intermediate leaderboard.
   *
   * @param entry leaderboard entry.
   */
  public void showMPIntermediate(LeaderboardEntry entry) {
    primaryStage.setTitle("Match Leaderboard");
    leaderboardScreenCtrl.setScoreLabel("Generating scores...");
    leaderboardScreenCtrl.setIntermediate(entry);
    primaryStage.setScene(leaderboard);
  }

  /**
   * Method for revealing the entries of a multiplayer leaderboard.
   */
  public void setMPLeaderboard() {
    leaderboardScreenCtrl.setScoreLabel("Scores");
    leaderboardScreenCtrl.refreshTop10();
  }

  /**
   * Shows the single-player waiting room.
   */
  public void showWaitingRoomScreenSP() {
    primaryStage.setTitle("Waiting...");
    waitingRoomCtrl.animate();
    primaryStage.setScene(waitingRoomSP);
  }

  /**
   * Shows the multi-player waiting room.
   */
  public void showWaitingRoomScreenMP() {
    primaryStage.setTitle("Waiting...");
    primaryStage.setScene(waitingRoomMP);
    mpWaitingRoomCtrl.startTimeline();
    mpWaitingRoomCtrl.startListening();
  }

  /**
   * Shows the activity panel scene.
   */
  public void showActivityOverview() {
    activityCtrl.refresh();
    primaryStage.setTitle("Activity Panel");
    primaryStage.setScene(activityList);
  }

  /**
   * Closes the edit activity popup.
   */
  public void closeActivityPopUp() {
    popup.close();
  }

  /**
   * Shows the activity popup.
   *
   * @param nextScreen the type of action the admin wants to do
   * @param act        the activity the admin wants to use
   */
  public void showActivityPopUp(Activity act, NextScreen nextScreen) {
    activityPopUpCtrl.setType(act, nextScreen);
    activityPopUpCtrl.disableValidator();
    popup.setTitle("Activity Panel");
    popup.setScene(activityPopUp);
    popup.show();
  }

  /**
   * Method to enable leaderboard home buttons.
   */
  public void showButtons() {
    leaderboardScreenCtrl.enableHomeButtons();
  }

  /**
   * Method to hide leaderboard home buttons.
   */
  public void hideButtons() {
    leaderboardScreenCtrl.disableHomeButtons();
  }
}

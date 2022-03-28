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
import commons.GameEntity;
import commons.LeaderboardEntry;
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

  private MultipleChoiceCtrl moreExpensiveCtrl;
  private Scene moreExpensive;

  private LeaderboardScreenCtrl leaderboardScreenCtrl;
  private Scene leaderboard;

  private WaitingRoomCtrl waitingRoomCtrl;
  private Scene waitingRoomSP;

  private MPWaitingRoomCtrl mpWaitingRoomCtrl;
  private Scene waitingRoomMP;

  private QuoteOverviewCtrl overviewCtrl;
  private Scene overview;

  private ActivityOverviewCtrl activityCtrl;
  private Scene activityList;

  private AddQuoteCtrl addCtrl;
  private Scene add;

  private ActivityPopUpCtrl activityPopUpCtrl;
  private Scene activityPopUp;

  /**
   * Initializes the main controller.
   * @param primaryStage  the top level JavaFX container.
   * @param overview      a pair of the QuoteOverview controller and the parent.
   * @param add           a pair of the AddQuote controller and the parent.
   * @param entry         a pair of the EntryScreen controller and the parent.
   * @param name          a pair of the NamePopup controller and the parent.
   * @param choose        a pair of the ChooseScreen controller and the parent.
   * @param moreExpensive a pair of the MultipleChoiceScreen controller and the parent.
   * @param leaderboard   a pair of the LeaderboardScreen controller and the parent.
   * @param waitingRoomSP a pair of the (single-player) WaitingRoomScreen controller and the parent.
   * @param waitingRoomMP a pair of the (multi-player) WaitingRoomScreen controller and the parent.
   * @param activityOverview
   * @param activityPopUp
   */
  public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                         Pair<AddQuoteCtrl, Parent> add, Pair<EntryCtrl, Parent> entry,
                         Pair<NamePopupCtrl, Parent> name, Pair<ChooseScreenCtrl, Parent> choose,
                         Pair<MultipleChoiceCtrl, Parent> moreExpensive,
                         Pair<LeaderboardScreenCtrl, Parent> leaderboard,
                         Pair<WaitingRoomCtrl, Parent> waitingRoomSP,
                         Pair<MPWaitingRoomCtrl, Parent> waitingRoomMP,
                         Pair<ActivityOverviewCtrl, Parent> activityOverview,
                         Pair<ActivityPopUpCtrl, Parent> activityPopUp) {
    this.primaryStage = primaryStage;
    this.overviewCtrl = overview.getKey();
    this.overview = new Scene(overview.getValue());

    this.addCtrl = add.getKey();
    this.add = new Scene(add.getValue());

    this.entryCtrl = entry.getKey();
    this.entry = new Scene(entry.getValue());

    this.namePopupCtrl = name.getKey();
    this.name = new Scene(name.getValue());

    this.chooseScreenCtrl = choose.getKey();
    this.choose = new Scene(choose.getValue());

    this.moreExpensiveCtrl = moreExpensive.getKey();
    this.moreExpensive = new Scene(moreExpensive.getValue());

    this.leaderboardScreenCtrl = leaderboard.getKey();
    this.leaderboard = new Scene(leaderboard.getValue());

    this.waitingRoomCtrl = waitingRoomSP.getKey();
    this.waitingRoomSP = new Scene(waitingRoomSP.getValue());

    this.mpWaitingRoomCtrl = waitingRoomMP.getKey();
    this.waitingRoomMP = new Scene(waitingRoomMP.getValue());

    this.activityCtrl = activityOverview.getKey();
    this.activityList = new Scene(activityOverview.getValue());

    this.activityPopUpCtrl = activityPopUp.getKey();
    this.activityPopUp = new Scene(activityPopUp.getValue());

    showEntry();
    primaryStage.show();

    this.popup = new Stage();
    this.popup.setMinWidth(500);
    this.popup.setMinHeight(280);
    this.popup.initModality(Modality.APPLICATION_MODAL);
    this.popup.initOwner(primaryStage);
  }

  /**
   * Shows the overview screen.
   */
  public void showOverview() {
    primaryStage.setTitle("Quotes: Overview");
    primaryStage.setScene(overview);
    overviewCtrl.refresh();
  }

  /**
   * Shows the add quote screen.
   */
  public void showAdd() {
    primaryStage.setTitle("Quotes: Adding Quote");
    primaryStage.setScene(add);
    add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
  }

  /**
   * Shows the game entry screen.
   */
  public void showEntry() {
    primaryStage.setTitle("Quizzzz");
    primaryStage.setScene(entry);
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
  }

  /**
   * Shows the multiple choice game screen.
   *
   * @param type the type of the game
   */
  public void showMoreExpensive(GameEntity.Type type) {
    primaryStage.setTitle("Quizzzz");
    moreExpensiveCtrl.setType(type);
    primaryStage.setScene(moreExpensive);
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
    leaderboardScreenCtrl.setMultiplayer(entry);
    primaryStage.setScene(leaderboard);
  }

  /**
   * Shows the single-player waiting room.
   */
  public void showWaitingRoomScreenSP() {
    primaryStage.setTitle("Waiting...");
    primaryStage.setScene(waitingRoomSP);
  }

  /**
   * Shows the multi-player waiting room.
   */
  public void showWaitingRoomScreenMP() {
    primaryStage.setTitle("Waiting...");
    primaryStage.setScene(waitingRoomMP);
  }

  /**
   * Shows the activity panel scene.
   */
  public void showActivityOverview(){
    activityCtrl.refresh();
    primaryStage.setTitle("Activity Panel");
    primaryStage.setScene(activityList);
  }

  public void closeActivityPopUp(){
    popup.close();
  }

  public void showActivityPopUp(NextScreen nextScreen){
      activityPopUpCtrl.setType(nextScreen);
      activityPopUpCtrl.disableValidator();
    popup.setTitle("Activity Panel");
    popup.setScene(activityPopUp);
    popup.show();
  }
}
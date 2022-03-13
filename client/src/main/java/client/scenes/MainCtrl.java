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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The main controller of the client application.
 */
public class MainCtrl {

  private Stage primaryStage;

  private EntryCtrl entryCtrl;
  private Scene entry;

  private NamePopupCtrl namePopupCtrl;
  private Scene name;

  private ChooseScreenCtrl chooseScreenCtrl;
  private Scene choose;

  private MultipleChoiceCtrl multipleChoiceCtrl;
  private Scene multipleChoice;

  private LeaderboardScreenCtrl leaderboardScreenCtrl;
  private Scene leaderboard;

  private QuoteOverviewCtrl overviewCtrl;
  private Scene overview;

  private AddQuoteCtrl addCtrl;
  private Scene add;

  /**
   * Initializes the main controller.
   *
   * @param primaryStage   the top level JavaFX container.
   * @param overview       a pair of the QuoteOverview controller and the parent.
   * @param add            a pair of the AddQuote controller and the parent.
   * @param entry          a pair of the EntryScreen controller and the parent.
   * @param name           a pair of the NamePopup controller and the parent.
   * @param choose         a pair of the ChooseScreen controller and the parent.
   * @param multipleChoice a pair of the MultipleChoiceScreen controller and the parent.
   * @param leaderboard    a pair of the LeaderboardScreen controller and the parent.
   */
  public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                         Pair<AddQuoteCtrl, Parent> add, Pair<EntryCtrl, Parent> entry,
                         Pair<NamePopupCtrl, Parent> name, Pair<ChooseScreenCtrl, Parent> choose,
                         Pair<MultipleChoiceCtrl, Parent> multipleChoice,
                         Pair<LeaderboardScreenCtrl, Parent> leaderboard) {
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

    this.multipleChoiceCtrl = multipleChoice.getKey();
    this.multipleChoice = new Scene(multipleChoice.getValue());

    this.leaderboardScreenCtrl = leaderboard.getKey();
    this.leaderboard = new Scene(leaderboard.getValue());


    showChooseScreen();
    primaryStage.show();
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
  }

  /**
   * Shows the name popup to enter the name.
   */
  public void showNamePopup() {
    primaryStage.setTitle("Choose your name!");
    primaryStage.setScene(name);
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
   */
  public void showMultipleChoice() {
    primaryStage.setTitle("Quizzzz");
    primaryStage.setScene(multipleChoice);
  }

  /**
   * Shows the leaderboard screen.
   */
  public void showLeaderboard(String type) {
    switch (type) {
      case "global":
        primaryStage.setTitle("Quizzzz Leaderboard");
        leaderboardScreenCtrl.setSingleplayer();
        primaryStage.setScene(leaderboard);
        leaderboardScreenCtrl.refreshTop5();
        break;
      case "multiplayer":
        primaryStage.setTitle("Match Leaderboard");
        primaryStage.setScene(leaderboard);
        break;
      default:
        break;
    }
  }
}
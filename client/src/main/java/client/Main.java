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

package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.*;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class of the client application.
 */
public class Main extends Application {

  private static final Injector INJECTOR = createInjector(new MyModule());
  private static final MyFXML FXML = new MyFXML(INJECTOR);

  /**
   * The start point of the client application.
   *
   * @param args the command line arguments passed to the application.
   * @throws URISyntaxException if a string could not be parsed as a URL reference.
   * @throws IOException        if an IOException occurred.
   */
  public static void main(String[] args) throws URISyntaxException, IOException {
    launch();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(Stage primaryStage) throws IOException {

    var questionGame =
        FXML.load(QuestionGameCtrl.class, "client", "scenes", "MoreExpensiveScreen.fxml");
    var entry = FXML.load(EntryCtrl.class, "client", "scenes", "EntryScreen.fxml");
    var choose = FXML.load(ChooseScreenCtrl.class, "client", "scenes", "ChooseScreen.fxml");
    var name = FXML.load(NamePopupCtrl.class, "client", "scenes", "NamePopup.fxml");
    var leaderboard =
        FXML.load(LeaderboardScreenCtrl.class, "client", "scenes", "LeaderboardScreen.fxml");
    var waitingRoomSP =
        FXML.load(WaitingRoomCtrl.class, "client", "scenes", "WaitingRoomScreen.fxml");
    var waitingRoomMP =
        FXML.load(MPWaitingRoomCtrl.class, "client", "scenes", "MPWaitingRoomScreen.fxml");
    var countdown = FXML.load(CountdownCtrl.class, "client", "scenes", "Countdown.fxml");
    var activityOverview =
        FXML.load(ActivityOverviewCtrl.class, "client", "scenes", "ActivityOverviewScreen.fxml");
    var activityPopUp =
        FXML.load(ActivityPopUpCtrl.class, "client", "scenes", "ActivityPopUpScreen.fxml");

    primaryStage.setMinWidth(900);
    primaryStage.setMinHeight(408);

    var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
    mainCtrl.initialize(primaryStage, entry, name, choose, questionGame,
        leaderboard, waitingRoomSP, waitingRoomMP, countdown, activityOverview, activityPopUp);
  }
}

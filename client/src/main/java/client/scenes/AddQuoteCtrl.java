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

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import commons.Quote;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * The controller class of the AddQuote screen.
 */
public class AddQuoteCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @FXML
  private TextField firstName;

  @FXML
  private TextField lastName;

  @FXML
  private TextField quote;

  /**
   * Injects the server and main controller into this class.
   *
   * @param server   the serverUtils instance that is going to be injected
   * @param mainCtrl the main controller instance that is going to be injected
   */
  @Inject
  public AddQuoteCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.mainCtrl = mainCtrl;
    this.server = server;

  }

  /**
   * Clears the fields and shows the overview.
   */
  public void cancel() {
    clearFields();
    // mainCtrl.showOverview();
  }

  /**
   * Tries to send the quote to the server, clears the fields, shows the overview.
   */
  public void ok() {

    clearFields();
    //  mainCtrl.showOverview();
  }

  /**
   * Returns a new quote that is made from the entered values.
   *
   * @return a new quote that is made from the entered values
   */
  private Quote getQuote() {
    var p = new Person(firstName.getText(), lastName.getText());
    var q = quote.getText();
    return new Quote(p, q);
  }

  /**
   * Clears all the fields.
   */
  private void clearFields() {
    firstName.clear();
    lastName.clear();
    quote.clear();
  }

  /**
   * Checks for an enter or escape key press.
   *
   * @param e the KeyEvent which indicates which key is pressed
   */
  public void keyPressed(KeyEvent e) {
    switch (e.getCode()) {
      case ENTER:
        ok();
        break;
      case ESCAPE:
        cancel();
        break;
      default:
        break;
    }
  }
}
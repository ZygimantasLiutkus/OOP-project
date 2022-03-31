package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Popup class to edit the activity table.
 */
public class ActivityPopUpCtrl {

  private final ServerUtils server;
  private final MainCtrl main;
  private final ActivityOverviewCtrl activityCtrl;
  private NextScreen type;

  @FXML
  private TextField title;

  @FXML
  private TextField id;

  @FXML
  private TextField consumption;

  @FXML
  private TextField imagePath;

  @FXML
  private Label textTitle;

  @FXML
  private Button button;

  @FXML
  private Label validator;

  /**
   * Constructor for the class.
   *
   * @param server       the ServerUtils reference
   * @param main         the MainCtrl reference
   * @param activityCtrl the ActivityOverviewCtrl reference
   */
  @Inject
  public ActivityPopUpCtrl(ServerUtils server, MainCtrl main,
                           ActivityOverviewCtrl activityCtrl) {
    this.main = main;
    this.server = server;
    this.activityCtrl = activityCtrl;
  }

  /**
   * Method that disables the validator text when showing the popup.
   */
  public void disableValidator() {
    if (type.equals(NextScreen.Update)) {
      textTitle.setText("Update an activity");
    } else {
      textTitle.setText("Add an activity");
    }
    validator.setVisible(false);
  }

  /**
   * Method that sets the type of the next scene.
   *
   * @param type either an "update" screen or an "add" screen
   * @param act  the activity the admin wants to use
   */
  public void setType(Activity act, NextScreen type) {
    if (act != null) {
      this.id.setText(act.getId());
      this.title.setText(act.getTitle());
      this.consumption.setText(Integer.toString(act.getConsumption_in_wh()));
      this.imagePath.setText(act.getImage_path());
    }
    this.type = type;
  }

  /**
   * Method that submits the data for a new activity.
   * Also calls for validation of raw data.
   */
  public void submit() {
    validator.setVisible(false);
    validate();
    if (!validator.isVisible()) {
      if (type.equals(NextScreen.Add)) {
        submitAdd();
      } else {
        submitUpdate();
      }
      id.setText("");
      title.setText("");
      consumption.setText("");
      imagePath.setText("");
    }
  }

  /**
   * Method to validate the data for potential errors when creating an activity.
   */
  public void validate() {
    if (id.getText().equals("") || title.getText().equals("") || consumption.getText().equals("")
        || imagePath.getText().equals("")) {
      validator.setText("This is not a valid activity!");
      validator.setVisible(true);
    } else if (consumption.getText().contains("-") || consumption.getText().equals("0")) {
      validator.setText("Type a valid consumption!");
      validator.setVisible(true);
    } else if (server.getActivityById(id.getText()) != null && type.equals(NextScreen.Add)) {
      validator.setText("This activity already exists! Change the id.");
      validator.setVisible(true);
    } else if (server.getActivityById(id.getText()) == null && type.equals(NextScreen.Update)) {
      validator.setText("This activity does not exist!");
      validator.setVisible(true);
    } else {
      try {
        Integer.parseInt(consumption.getText());
      } catch (NumberFormatException e) {
        validator.setText("That is not a number!");
        validator.setVisible(true);
      }
    }
  }

  /**
   * Method that creates a new activity.
   */
  public void submitAdd() {
    var act =
        new Activity(id.getText(), title.getText(), Integer.parseInt(consumption.getText()),
            imagePath.getText());
    server.addActivity(act);
    activityCtrl.refresh();
    main.closeActivityPopUp();
  }


  /**
   * Method that updates an activity.
   */
  public void submitUpdate() {
    var act =
        new Activity(id.getText(), title.getText(), Integer.parseInt(consumption.getText()),
            imagePath.getText());
    server.updateActivity(act);
    activityCtrl.refresh();
    main.closeActivityPopUp();
  }
}

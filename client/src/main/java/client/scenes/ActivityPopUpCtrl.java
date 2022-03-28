package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

  @Inject
  public ActivityPopUpCtrl(ServerUtils server, MainCtrl main,
                           ActivityOverviewCtrl activityCtrl) {
    this.main = main;
    this.server = server;
    this.activityCtrl = activityCtrl;
  }

  public void disableValidator() {
    validator.setVisible(false);
  }

  public void setType(NextScreen type) {
    this.type = type;
  }

  public void submit() {
    if (type.equals(NextScreen.Add)) {
      submitAdd();
    } else {
      submitUpdate();
    }
  }

  public void submitAdd() {
    if (id.getText().equals("") || title.getText().equals("") || consumption.getText().equals("") ||
        imagePath.getText().equals("")) {
      validator.setText("This is not a valid activity!");
      validator.setVisible(true);
    } else if (consumption.getText().contains("-")) {
      validator.setText("Type a valid consumption!");
      validator.setVisible(true);
    } else {
      try {
        Integer.parseInt(consumption.getText());
      } catch (NumberFormatException e) {
        validator.setText("That is not a number!");
        validator.setVisible(true);
      }
    }
    if (!validator.isVisible()) {
      var act =
          new Activity(id.getText(), title.getText(), Integer.parseInt(consumption.getText()),
              imagePath.getText());
      server.addActivity(act);
      activityCtrl.refresh();
      main.closeActivityPopUp();
    }

  }

  public void submitUpdate() {
    var act = new Activity(id.getText(), title.getText(), Integer.parseInt(consumption.getText()),
        imagePath.getText());
    activityCtrl.refresh();
    main.closeActivityPopUp();
  }
}

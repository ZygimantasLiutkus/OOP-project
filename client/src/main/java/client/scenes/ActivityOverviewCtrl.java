package client.scenes;

import client.utils.NextScreen;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * Controller class for the activity overview panel.
 */
public class ActivityOverviewCtrl implements Initializable {

  private final MainCtrl main;
  private final ServerUtils server;
  private ObservableList<Activity> data;

  @FXML
  private Label textLabel;

  @FXML
  private ImageView house;

  @FXML
  private Button addActivity;

  @FXML
  private Button updateActivity;

  @FXML
  private TableView<Activity> activityTable;

  @FXML
  private TableColumn activityId;

  @FXML
  private TableColumn title;

  @FXML
  private TableColumn consumption;

  @FXML
  private TableColumn imagePath;

  /**
   * Constructor for the class.
   *
   * @param main   the MainCtrl reference
   * @param server the ServerUtils reference
   */
  @Inject
  public ActivityOverviewCtrl(MainCtrl main, ServerUtils server) {
    this.main = main;
    this.server = server;
  }

  /**
   * Method to go back to the choose screen.
   */
  public void goHome() {
    main.showChooseScreen();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    activityId.setCellValueFactory(new PropertyValueFactory<Activity, String>("id"));
    title.setCellValueFactory(new PropertyValueFactory<Activity, String>("title"));
    consumption.setCellValueFactory(new PropertyValueFactory<Activity, Long>("consumption_in_wh"));
    imagePath.setCellValueFactory(new PropertyValueFactory<Activity, String>("image_path"));
  }

  /**
   * Method that refreshes the table after editing it.
   */
  public void refresh() {
    var activities = server.getAllActivities();
    data = FXCollections.observableList(activities);
    activityTable.setItems(data);
  }

  /**
   * Method to open the popup to add an activity.
   */
  public void addPopUp() {
    main.showActivityPopUp(null, NextScreen.Add);
  }

  /**
   * Method to open up the popup to update an activity.
   */
  public void updatePopUp() {
    Activity act = activityTable.getSelectionModel().getSelectedItem();
    main.showActivityPopUp(act, NextScreen.Update);
  }
}

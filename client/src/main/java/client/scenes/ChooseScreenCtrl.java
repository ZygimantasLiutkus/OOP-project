package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

/**
 * A template controller for the ChooseScreen scene.
 */
public class ChooseScreenCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  /**
   * Constructor for ChooseScreenCtrl.
   *
   * @param server   reference to the server the game will run on.
   * @param mainCtrl reference to the main controller.
   */
  @Inject
  public ChooseScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

}

package client.utils;

/**
 * Enum for selecting the next screen to be shown after the name is entered.
 */
public enum NextScreen {
  WaitingRoomScreen,
  MPWaitingRoomScreen,
  Update,
  Add,
  None // Use this if you want to stay on the same screen after the popup closes
}

package commons;

/**
 * Class that represents an emoji-message that can be sent in multiplayer games.
 */
public class Message {

  private String text;
  private String playerName;

  /**
   * For object mappers.
   */
  @SuppressWarnings("unused")
  public Message() {
  }

  /**
   * The constructor for the class.
   *
   * @param text       string that represents which emoji is being sent
   * @param playerName the name of the player who sent the message
   */
  public Message(String text, String playerName) {
    this.text = text;
    this.playerName = playerName;
  }

  /**
   * Getter for the name of the emoji.
   *
   * @return the name of the emoji
   */
  public String getText() {
    return text;
  }

  /**
   * Setter for the name of the emoji.
   *
   * @param text the name of the emoji
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Getter for the name of the player.
   *
   * @return the name of the player
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Setter for the name of the player.
   *
   * @param playerName the name of the player
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
}

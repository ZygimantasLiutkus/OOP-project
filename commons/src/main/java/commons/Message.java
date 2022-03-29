package commons;

/**
 * Class that represents an emoji-message that can be sent in multiplayer games.
 */
public class Message {

  private String emojiName;
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
   * @param emojiName  string that represents which emoji is being sent
   * @param playerName the name of the player who sent the message
   */
  public Message(String emojiName, String playerName) {
    this.emojiName = emojiName;
    this.playerName = playerName;
  }

  /**
   * Getter for the name of the emoji.
   *
   * @return the name of the emoji
   */
  public String getEmojiName() {
    return emojiName;
  }

  /**
   * Setter for the name of the emoji.
   *
   * @param emojiName the name of the emoji
   */
  public void setEmojiName(String emojiName) {
    this.emojiName = emojiName;
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

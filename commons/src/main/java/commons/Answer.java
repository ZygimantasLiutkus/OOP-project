package commons;

/**
 * Class that will represent the answer given by the server.
 */
public class Answer {

  private String feedback;
  private Player player;
  private int score;
  private int added;

  /**
   * The constructor.
   *
   * @param feedback the decision made by the server
   * @param player   the player that submitted an answer
   * @param score    the final obtained score
   * @param added    the last added score after a round
   */
  public Answer(String feedback, Player player, int score, int added) {
    this.feedback = feedback;
    this.player = player;
    this.score = score;
    this.added = added;
  }

  /**
   * Getter for the feedback.
   *
   * @return a string representing the status of an answer
   */
  public String getFeedback() {
    return feedback;
  }

  /**
   * Getter for the player.
   *
   * @return a player that has submitted an answer
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Getter for the score.
   *
   * @return the last obtained score
   */
  public int getScore() {
    return score;
  }

  /**
   * Getter for the obtained score.
   *
   * @return the last added score to the total
   */
  public int getAdded() {
    return added;
  }
}

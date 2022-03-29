package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The players will be stored with the outline defined below inside a table with the same name.
 */
@Entity(name = "Player")
@Table(name = "Player")
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  public Long id;
  private int score = 0;
  private String name;
  private String selectedAnswer = "0";
  private Long gameId = 0L;

  /**
   * For object mappers.
   */
  @SuppressWarnings("unused")
  public Player() {
  }

  /**
   * A constructor for the player.
   * This is only used to initialise a player after they have connected to a server & added a name.
   * The score will be set to 0, and we will not change yet the game session ID
   * The selected answer will be set to 0, can change to answer1, answer2 or answer3 mid-game
   *
   * @param name the name a player has chosen
   */
  public Player(String name) {
    this.name = name;
  }

  /**
   * Temporary constructor for testing answer posting in Postman.
   *
   * @param id             id of the player
   * @param selectedAnswer selected answer (wh) in string format
   */
  public Player(Long id, String selectedAnswer) {
    this.id = id;
    this.selectedAnswer = selectedAnswer;
  }

  /**
   * A getter for the name.
   *
   * @return a string that represents the name that was saved for the player
   */
  public String getName() {
    return name;
  }

  /**
   * A setter that so that we can change a name (not recommendable).
   *
   * @param name a new string that should represent the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A getter to obtain the player's score.
   *
   * @return an integer that shows the player's score
   */
  public int getScore() {
    return score;
  }

  /**
   * A setter for the player's score.
   *
   * @param score an integer that should be bigger than the previous number
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * A getter to obtain the player's selected answer.
   *
   * @return an integer that indicates the player's selected answer
   */
  public String getSelectedAnswer() {
    return selectedAnswer;
  }

  /**
   * A setter for the player's selected answer.
   *
   * @param selectedAnswer none, answer1, answer2 or answer3
   *                       indicating what answer is selected (0 if none)
   */
  public void setSelectedAnswer(String selectedAnswer) {
    this.selectedAnswer = selectedAnswer;
  }

  /**
   * A getter for the player ID.
   *
   * @return a long that distinguishes between players
   */
  public Long getId() {
    return id;
  }

  /**
   * A setter that can change a player's id (not recommendable).
   *
   * @param id a long that is generated using Identity
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Getter for the game.
   *
   * @return a long representing the id of the last joined game.
   */
  public Long getGameId() {
    return gameId;
  }

  /**
   * Setter for the game id.
   *
   * @param id the id of the game the player has joined.
   */
  public void setGameId(Long id) {
    this.gameId = id;
  }

  /**
   * A method that uses an API supportive version of the "equals" method.
   *
   * @param obj a random type of object to be compared to
   * @return a boolean whether these 2 objects are the same or not
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  /**
   * A method that uses an API supportive version of hashing.
   *
   * @return a hash code of the Player object
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * A method that uses an API supportive method of transforming data into a string.
   *
   * @return a string containing every detail about the player
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
  }
}

package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class that supports every data about a game.
 */
@Entity(name = "GameEntity")
@Table(name = "GameEntity")
public class GameEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  public Long id;
  private Type type;
  private String status;
  @OneToMany(cascade = CascadeType.PERSIST)
  private List<Player> players = new ArrayList<>();
  @OneToMany(cascade = CascadeType.PERSIST)
  private List<Question> questions = new ArrayList<>();

  /**
   * For object mappers.
   */
  @SuppressWarnings("unused")
  public GameEntity() {
    this.status = "WAITING";
  }

  /**
   * The constructor of the class.
   *
   * @param type      the type of the game (singleplayer or multiplayer).
   * @param questions the list of 20 chosen questions
   */
  public GameEntity(Type type, List<Question> questions) {
    this.type = type;
    this.status = "WAITING";
    this.questions = questions;
  }

  /**
   * Constructor to only support status.
   *
   * @param status the desired status
   */
  public GameEntity(String status) {
    this.status = status;
  }

  /**
   * A getter for the type.
   *
   * @return a string value of the game type.
   */
  public Type getType() {
    return this.type;
  }

  /**
   * A setter for the type.
   *
   * @param t an enum of the game type.
   */
  public void setType(Type t) {
    this.type = t;
  }

  /**
   * A getter for the status.
   *
   * @return a string from a pre-made list
   */
  public String getStatus() {
    return status;
  }

  /**
   * A setter for the class.
   *
   * @param status the string containing the status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * A getter for the list of players.
   *
   * @return a list containing all players enrolled to a game
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * A setter for the list of players.
   *
   * @param players the list of players
   */
  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  /**
   * A getter for the list of all selected questions.
   *
   * @return a list containing 20 questions selected by the server.
   */
  public List<Question> getQuestions() {
    return questions;
  }

  /**
   * A setter for the question list.
   *
   * @param q a list of questions.
   */
  public void setQuestions(List<Question> q) {
    this.questions = q;
  }

  /**
   * A getter for the id.
   *
   * @return a long generated using Identity
   */
  public Long getId() {
    return this.id;
  }

  /**
   * A setter for the id.
   *
   * @param id a long generated using Identity.
   */
  public void setId(Long id) {
    this.id = id;
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

  /**
   * Adds a player to the game's list of players.
   *
   * @param player the player that entered a waiting room
   */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Method that adds a question to the list.
   *
   * @param q a derived question
   */
  public void addQuestion(Question q) {
    this.questions.add(q);
  }

  /**
   * An enum describing the type of the game.
   */
  public enum Type {
    SINGLEPLAYER,
    MULTIPLAYER
  }
}
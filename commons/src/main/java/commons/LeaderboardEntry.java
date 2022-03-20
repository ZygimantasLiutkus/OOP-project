package commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * A leaderboard entry.
 */
@Entity(name = "Leaderboard")
public class LeaderboardEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private String name;
  private int score;
  private int ranking; // only used & computed by the frontend

  /**
   * For object mappers.
   */
  public LeaderboardEntry() {
  }

  /**
   * Constructor for the class.
   *
   * @param name  the username that was entered
   * @param score the last obtained score
   */
  public LeaderboardEntry(String name, int score) {
    this.name = name;
    this.score = score;
  }

  /**
   * A getter for the id.
   *
   * @return a long generated using Identity
   */
  public Long getId() {
    return id;
  }

  /**
   * A setter for id.
   *
   * @param id a long generated using Identity
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * A getter for the username.
   *
   * @return a unique name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the username.
   *
   * @param name a unique name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A getter for the score.
   *
   * @return an integer
   */
  public int getScore() {
    return score;
  }

  /**
   * A getter for the rank.
   *
   * @return the rank
   */
  public int getRanking() {
    return this.ranking;
  }

  /**
   * A setter for the rank.
   *
   * @param ranking the rank to set for this entry
   */
  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  /**
   * A method that uses an API supportive version of the "equals" method.
   *
   * @param obj a random type of object to be compared to
   * @return a boolean whether these 2 objects are the same or not
   */
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
}

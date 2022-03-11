package commons;

import javax.persistence.*;

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
   * A getter for the score.
   *
   * @return an integer
   */
  public int getScore() {
    return score;
  }
}

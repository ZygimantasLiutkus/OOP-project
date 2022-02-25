package commons;

import javax.persistence.*;

@Entity(name = "Leaderboard")
public class LeaderboardEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private String name;
  private int score;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName(){
    return name;
  }

  public int getScore(){
    return score;
  }
}

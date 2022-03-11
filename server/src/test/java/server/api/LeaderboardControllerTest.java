package server.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import commons.LeaderboardEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the leaderboard controller.
 */
public class LeaderboardControllerTest {

  private LeaderboardController sut;
  private TestLeaderboardRepository repo;

  /**
   * Returns a new leaderboard's entry.
   *
   * @param s the username
   * @param x the final score
   * @return a new entry
   */
  private static LeaderboardEntry getEntry(String s, int x) {
    return new LeaderboardEntry(s, x);
  }

  /**
   * Initializes LeaderboardControllerTest.
   */
  @BeforeEach
  public void setUp() {
    repo = new TestLeaderboardRepository();
    sut = new LeaderboardController(repo);
  }

  /**
   * Tests if you can add a null name.
   */
  @Test
  public void cannotAddNullName() {
    var actual = sut.add(getEntry(null, 10));
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Tests if you can add a negative score.
   */
  @Test
  public void cannotAddNegScore() {
    var actual = sut.add(getEntry("Bob", -1));
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Tests whether the database is used when adding an entry.
   */
  @Test
  public void databaseIsUsed() {
    sut.add(getEntry("Bob", 0));
   assertTrue(repo.calledMethods.contains("save"));
  }

}

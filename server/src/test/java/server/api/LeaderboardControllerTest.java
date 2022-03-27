package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import commons.LeaderboardEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestLeaderboardRepository;

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

  /**
   * Tests whether the findAll method is used when calling getAll.
   */
  @Test
  public void findAllIsUsed() {
    sut.add(getEntry("Bob", 0));
    sut.getAll();
    assertTrue(repo.calledMethods.contains("findAll"));
  }

  /**
   * Tests whether the correct name is created when adding a second entry with the same name.
   */
  @Test
  public void testNameTwice() {
    sut.add(getEntry("name1", 100));
    LeaderboardEntry e = sut.add(getEntry("name1", 200)).getBody();
    assertEquals(e.getName(), "name1#1");
  }

  /**
   * Tests whether the correct name is created when adding a third entry with the same name.
   */
  @Test
  public void testNameTrice() {
    sut.add(getEntry("name1", 100));
    sut.add(getEntry("name1", 200));
    LeaderboardEntry e = sut.add(getEntry("name1", 300)).getBody();
    assertEquals(e.getName(), "name1#2");
  }
}

package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test class for a leaderboard entry.
 */
public class LeaderboardTest {

  /**
   * Tests whether the constructor works properly.
   */
  @Test
  public void testConstructor() {
    LeaderboardEntry e = new LeaderboardEntry("Bob", 44);
    assertEquals(e.getScore(), 44);
    assertEquals(e.getName(), "Bob");
  }

  /**
   * Test for empty constructor.
   */
  @Test
  void constructorEmpty() {
    LeaderboardEntry e = new LeaderboardEntry();
    assertNotNull(e);
  }

  /**
   * Tests whether the id works properly.
   */
  @Test
  public void testID() {
    LeaderboardEntry e = new LeaderboardEntry("Bob", 44);
    e.setId((long) 1);
    assertEquals(e.getId(), 1);
  }

  /**
   * Tests whether the name works properly.
   */
  @Test
  public void testName() {
    LeaderboardEntry e = new LeaderboardEntry("Bob", 44);
    e.setName("Bobbie");
    assertEquals(e.getName(), "Bobbie");
  }

  /**
   * Tests whether the ranking works properly.
   */
  @Test
  public void testRanking() {
    LeaderboardEntry e = new LeaderboardEntry("Bob", 44);
    e.setRanking(5);
    assertEquals(e.getRanking(), 5);
  }

  /**
   * Tests whether the equals works properly.
   */
  @Test
  public void testEquals() {
    LeaderboardEntry e1 = new LeaderboardEntry("Bob", 44);
    LeaderboardEntry e2 = new LeaderboardEntry("Bob", 44);
    assertEquals(e1, e2);
  }
}

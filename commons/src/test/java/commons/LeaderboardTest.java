package commons;

import static org.junit.jupiter.api.Assertions.*;

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

}

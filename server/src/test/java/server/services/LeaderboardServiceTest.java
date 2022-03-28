package server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import commons.GameEntity;
import commons.LeaderboardEntry;
import commons.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the LeaderboardService.
 */
public class LeaderboardServiceTest {
  /**
   * Tests if the constructor gives a non-null object.
   */
  @Test
  public void testLeaderboardConstructor() {
    LeaderboardService ls = new LeaderboardService();
    assertNotNull(ls);
  }

  /**
   * Tests the leaderboard generator.
   */
  @Test
  public void testLeaderboardGenerator() {
    GameEntity game = new GameEntity();
    Player p1 = new Player("p1");
    Player p2 = new Player("p2");
    p1.setScore(1);
    p2.setScore(2);
    game.addPlayer(p1);
    game.addPlayer(p2);
    LeaderboardEntry le1 = new LeaderboardEntry("p1", 1);
    LeaderboardEntry le2 = new LeaderboardEntry("p2", 2);
    List<LeaderboardEntry> lb = new ArrayList<LeaderboardEntry>();
    lb.add(le1);
    lb.add(le2);
    LeaderboardService ls = new LeaderboardService();
    assertEquals(lb, ls.generateLeaderboard(game));
  }
}

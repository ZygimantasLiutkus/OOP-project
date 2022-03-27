package server.services;

import commons.GameEntity;
import commons.LeaderboardEntry;
import commons.Player;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Class to create a leaderboard from a game.
 */
@Service
public class LeaderboardService {

  /**
   * The constructor of the LeaderboardService.
   */
  public LeaderboardService() {
  }

  /**
   * Creates a leaderboard from a game.
   *
   * @param game the game to create a leaderboard from
   * @return the leaderboard created from the game
   */
  public List<LeaderboardEntry> generateLeaderboard(GameEntity game) {
    List<LeaderboardEntry> list = new ArrayList<>();

    for (Player player : game.getPlayers()) {
      LeaderboardEntry newEntry = new LeaderboardEntry(player.getName(), player.getScore());
      list.add(newEntry);
    }
    return list;
  }
}

package server.database;

import commons.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The leaderboard Entry repository.
 */
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {
}

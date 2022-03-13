package server.database;

import commons.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for player.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}

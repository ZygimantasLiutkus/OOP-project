package server.database;

import commons.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for game
 */
public interface GameEntityRepository extends JpaRepository<GameEntity, Long> {
}

package server.database;

import commons.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameEntityRepository extends JpaRepository<GameEntity, Long> {
}

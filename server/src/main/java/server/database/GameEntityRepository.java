package server.database;

import commons.GameEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for game.
 */
public interface GameEntityRepository extends JpaRepository<GameEntity, Long> {

  /**
   * Method to filter the games by status.
   * Return a list of all matching games.
   *
   * @param status the status we want to filter
   * @return the list of all games that are found
   */
  public List<GameEntity> findByStatus(String status);

  /**
   * Method to filter the games by type.
   *
   * @param type the type we want to filter
   * @return the list of all games that are found
   */
  public List<GameEntity> findByType(GameEntity.Type type);
}

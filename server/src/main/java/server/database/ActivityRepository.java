package server.database;

import commons.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class for the activity repository.
 */
public interface ActivityRepository extends JpaRepository<Activity, String> {
}

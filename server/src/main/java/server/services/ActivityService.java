package server.services;

import commons.Activity;
import java.util.List;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

/**
 * Class that imports activities into the database.
 */
@Service
public class ActivityService {

  private final ActivityRepository repo;

  /**
   * Constructor for the activity service.
   *
   * @param repo repository of activities.
   */
  public ActivityService(ActivityRepository repo) {
    this.repo = repo;
  }

  /**
   * Saves an activity.
   *
   * @param activity - activity to be saved.
   * @return saved activity.
   */
  public Activity save(Activity activity) {
    return repo.save(activity);
  }

  /**
   * Saves a list of activities.
   *
   * @param list of activities to be saved.
   * @return save list of activities.
   */
  public Iterable<Activity> save(List<Activity> list) {
    return repo.saveAll(list);
  }

}

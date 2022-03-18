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

  public Activity save(Activity activity) {
    return repo.save(activity);
  }

  public Iterable<Activity> save(List<Activity> list) {
    return repo.saveAll(list);
  }

}

package server.api;

import commons.Activity;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

/**
 * Controller for the activities.
 */
@RestController
public class ActivityController {

  private final ActivityRepository repo;

  /**
   * Constructor for the class.
   *
   * @param repo the activity repository
   */
  public ActivityController(ActivityRepository repo) {
    this.repo = repo;
  }

  /**
   * GET request to retrieve all activities.
   *
   * @return a list of activities
   */
  @GetMapping(path = {"", "/"})
  public ResponseEntity<List<Activity>> getAll() {
    return ResponseEntity.ok(repo.findAll());
  }

  /**
   * POST request to add an activity.
   *
   * @param activity the new activity
   * @return the contents of the new activity
   */
  @PostMapping(path = {"", "/"})
  public ResponseEntity<Activity> add(@RequestBody Activity activity) {
    if (activity == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(repo.save(activity));
  }

}

package server.api;


import commons.Activity;
import java.util.List;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

/**
 * Controller for the activities.
 */
@RestController
@RequestMapping(path = "/api/activity")
public class ActivityController {

  private final ActivityRepository repo;
  private final Random random;

  /**
   * Constructor for the class.
   *
   * @param repo   the activity repository
   * @param random the random object
   */
  public ActivityController(ActivityRepository repo, Random random) {
    this.random = random;
    this.repo = repo;
  }

  /**
   * Returns if the supplied string is null or empty.
   *
   * @param s the string to check
   * @return true iff the string is null or empty
   */
  private static boolean isNullOrEmpty(String s) {
    return s == null || s.isEmpty();
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
    if (isNullOrEmpty(activity.getId()) || activity.getConsumption_in_wh() <= 0
        || isNullOrEmpty(activity.getTitle()) || isNullOrEmpty(activity.getImage_path())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(repo.save(activity));
  }

  /**
   * POST request to add a list of activities.
   *
   * @param activities a list of activities.
   * @return the list of activities.
   */
  @PostMapping(path = {"/import"})
  public ResponseEntity<List<Activity>> addAll(@RequestBody List<Activity> activities) {
    for (Activity a : activities) {
      if (isNullOrEmpty(a.getId()) || a.getConsumption_in_wh() <= 0
          || isNullOrEmpty(a.getTitle()) || isNullOrEmpty(a.getImage_path())) {
        return ResponseEntity.badRequest().build();
      }
      if (a.getConsumption_in_wh() == (int) a.getConsumption_in_wh()) {
        return ResponseEntity.ok(repo.saveAll(activities));
      }
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * Get a random activity from the list.
   * (Cannot be done without accessing the whole list)
   *
   * @return a random activity.
   */
  @GetMapping(path = "/random")
  public ResponseEntity<Activity> getRandom() {
    var idx = random.nextInt((int) repo.count());
    return ResponseEntity.ok(repo.findAll().get(idx));
  }

}

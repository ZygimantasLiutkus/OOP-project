package server.api;

import commons.LeaderboardEntry;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.LeaderboardRepository;


/**
 * The Leaderboard controller.
 */
@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

  private final LeaderboardRepository repo;

  /**
   * Constructor for the controller.
   *
   * @param repo the leaderboard repository
   */
  public LeaderboardController(LeaderboardRepository repo) {
    this.repo = repo;
  }

  /**
   * the GET endpoint.
   *
   * @return a list of all
   */
  @GetMapping
  public List<LeaderboardEntry> getAll() {
    return repo.findAll();
  }

  /**
   * A POST endpoint where you can't add the same name twice.
   *
   * @param entry a JSON containing a name and a score
   * @return a response (either OK or Bad Request)
   */
  @PostMapping
  public ResponseEntity<LeaderboardEntry> add(@RequestBody LeaderboardEntry entry) {
    if (entry.getName() == null || entry.getScore() < 0) {
      return ResponseEntity.badRequest().build();
    }
    int count = 0;
    for (LeaderboardEntry e : repo.findAll()) {
      // Checks if this name is already in the database
      String name = e.getName();
      int index = name.lastIndexOf("#");
      if (index > 0) {
        name = e.getName().substring(0, index);
      }
      if (name.equals(entry.getName())) {
        count++;
      }
    }
    if (count > 0) {
      // Adds #times after the name with 'times' being the amount of times this username is used
      entry.setName(entry.getName() + "#" + (count));
    }
    LeaderboardEntry saved = repo.save(entry);
    return ResponseEntity.ok(saved);
  }
}
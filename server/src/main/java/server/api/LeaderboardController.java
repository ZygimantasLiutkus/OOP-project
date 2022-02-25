package server.api;

import commons.LeaderboardEntry;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    for (LeaderboardEntry e : repo.findAll()) {
      if (e.getName().equals(entry.getName())) {
        return ResponseEntity.badRequest().build();
      }
    }
    LeaderboardEntry saved = repo.save(entry);
    return ResponseEntity.ok(saved);
  }


}

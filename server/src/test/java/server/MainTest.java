package server;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestActivityRepository;
import server.services.ActivityService;

/**
 * Tests the main class of the server.
 */
public class MainTest {

  public Main main;

  /**
   * Sets up the main.
   */
  @BeforeEach
  public void setup() {
    this.main = new Main();
  }

  /**
   * Tests the runner.
   */
  @Test
  public void testRunner() {
    ActivityService as = new ActivityService(new TestActivityRepository());
    assertNotNull(main.runner(as));
  }
}

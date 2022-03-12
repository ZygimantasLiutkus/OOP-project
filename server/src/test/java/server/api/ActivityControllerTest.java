package server.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import commons.Activity;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the activity controller.
 */
public class ActivityControllerTest {

  public TestActivityRepository repo;
  public MyRandom random;
  public int nextInt;

  private ActivityController sut;

  /**
   * Returns a new activity.
   *
   * @param s a string with some contents
   * @param x an integer bigger than 0
   * @return a new activity
   */
  private static Activity getActivity(String s, int x) {
    return new Activity(s, s, x, s);
  }

  /**
   * Initializes the ActivityController.
   */
  @BeforeEach
  public void setup() {
    random = new MyRandom();
    repo = new TestActivityRepository();
    sut = new ActivityController(repo, random);
  }

  /**
   * Tests if adding an activity also keeps the data correctly.
   */
  @Test
  public void addActivity() {
    var actual = sut.add(getActivity("A", 1));
    assertEquals(actual.getBody().getConsumption_in_wh(), 1);
    assertEquals(actual.getBody().getTitle(), "A");
    assertEquals(actual.getBody().getImage_path(), "A");
  }

  /**
   * Tests if you can add an activity with negative consumption.
   */
  @Test
  public void cannotAddNegativeConsumption() {
    var actual = sut.add(getActivity("A", -10));
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Tests if you can add an activity with an empty id.
   */
  @Test
  public void cannotHaveEmptyId() {
    var actual = sut.add(getActivity("", 10));
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Tests if adding activities has an impact on the big list.
   */
  @Test
  public void getAllTest() {
    sut.add(getActivity("A", 1));
    sut.add(getActivity("B", 1));
    assertEquals(sut.getAll().getBody().size(), 2);
  }

  /**
   * Tests if the database is used.
   */
  @Test
  public void databaseIsUsed() {
    sut.add(getActivity("A", 1));
    assertTrue(repo.used.contains("save"));
  }

  /**
   * Tests if the Random was called when asking for a random activity.
   */
  @Test
  public void randomSelect() {
    sut.add(getActivity("A", 1));
    sut.add(getActivity("B", 10));
    nextInt = 1;
    var actual = sut.getRandom();

    assertTrue(random.wasCalled);
  }

  /**
   * Extends the implementation of the Random class.
   */
  @SuppressWarnings("serial")
  public class MyRandom extends Random {

    public boolean wasCalled = false;

    /**
     * Returns the integer that was set as nextInt, sets the wasCalled to true.
     *
     * @param bound not used in this version
     * @return the next integer
     */
    @Override
    public int nextInt(int bound) {
      wasCalled = true;
      return nextInt;
    }
  }
}

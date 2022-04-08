package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import commons.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.TestActivityRepository;

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
    sut.getRandom();
    assertTrue(random.wasCalled);
  }

  /**
   * Test if getImagePathById() retrieves the correct image path.
   */
  @Test
  public void testGetImagePathById() {
    repo.deleteAll();
    Activity testActivity = new Activity("09-shower", "Taking a hot shower for 6 minutes",
        4000, "00/shower.png");
    repo.save(testActivity);
    String imagePath = sut.getImagePathById(testActivity.getId()).getBody();
    assertEquals("00/shower.png", imagePath);
  }

  /**
   * Test if getImagePathById() returns an error for an id that doesn't exist.
   */
  @Test
  public void testGetImagePathByIDNonexistent() {
    repo.deleteAll();
    assertEquals(BAD_REQUEST, sut.getImagePathById("0").getStatusCode());
  }

  /**
   * Test if update() updates the attributes of the activity with specific id.
   */
  @Test
  public void testUpdate() {
    repo.deleteAll();
    Activity a = getActivity("A", 1);
    Activity b = getActivity("B", 2);
    repo.save(a);
    ResponseEntity<Activity> re = sut.update(a.getId(), b);
    assertEquals(b.getTitle(), re.getBody().getTitle());
    assertEquals(b.getImage_path(), re.getBody().getImage_path());
    assertEquals(b.getConsumption_in_wh(), re.getBody().getConsumption_in_wh());
  }

  /**
   * Tests if update() returns an error for an id that does not exist.
   */
  @Test
  public void testUpdateNonexistant() {
    repo.deleteAll();
    assertEquals(BAD_REQUEST,
        sut.update(getActivity("A", 1).getId(), getActivity("A", 2)).getStatusCode());
  }

  /**
   * Tests if update() returns an error when trying to update with a null or empty title.
   */
  @Test
  public void testUpdateNoTitle() {
    repo.deleteAll();
    Activity a = getActivity("A", 1);
    repo.save(a);
    assertEquals(BAD_REQUEST,
        sut.update(a.getId(), new Activity("A", null, 1, "A")).getStatusCode());
    assertEquals(BAD_REQUEST, sut.update(a.getId(), new Activity("A", "", 1, "A")).getStatusCode());
  }

  /**
   * Tests if update() returns an error when trying to update with a less or equal to 0 consumption.
   */
  @Test
  public void testUpdateWrongConsumption() {
    repo.deleteAll();
    Activity a = getActivity("A", 1);
    repo.save(a);
    assertEquals(BAD_REQUEST,
        sut.update(a.getId(), new Activity("A", "A", 0, "A")).getStatusCode());
    assertEquals(BAD_REQUEST,
        sut.update(a.getId(), new Activity("A", "A", -5, "A")).getStatusCode());
  }

  /**
   * Tests if update() returns an error when trying to update with a null or empty path.
   */
  @Test
  public void testUpdateNoPath() {
    repo.deleteAll();
    Activity a = getActivity("A", 1);
    repo.save(a);
    assertEquals(BAD_REQUEST,
        sut.update(a.getId(), new Activity("A", "A", 1, null)).getStatusCode());
    assertEquals(BAD_REQUEST, sut.update(a.getId(), new Activity("A", "A", 1, "")).getStatusCode());
  }

  /**
   * Passing tests getAll activities method.
   */
  @Test
  public void getAllActivitiesPass() {
    List<Activity> activityList = new ArrayList<>();
    activityList.add(getActivity("a", 1));
    activityList.add(getActivity("s", 2));
    var actual = sut.addAll(activityList);
    assertEquals(1, actual.getBody().get(0).getConsumption_in_wh());
    assertEquals(2, actual.getBody().get(1).getConsumption_in_wh());
    assertEquals("s", actual.getBody().get(1).getTitle());
    assertEquals(2, actual.getBody().size());
  }

  /**
   * Failing tests getALl activities method.
   */
  @Test
  public void getAllActivitiesFail() {
    List<Activity> activityList = new ArrayList<>();
    activityList.add(getActivity("s", 1));
    activityList.add(getActivity("x", 2));
    var actual = sut.addAll(activityList);

    assertNotEquals(0, actual.getBody().size());
    assertNotEquals(getActivity("s", 1), actual.getBody().get(1));
    assertNotEquals(2, actual.getBody().get(0).getConsumption_in_wh());
  }

  /**
   * Tests that failed because of negative consumption.
   */
  @Test
  public void getAllActivitiesBadRequestCannotHaveNegativeConsumption() {
    List<Activity> activityList = new ArrayList<>();
    activityList.add(getActivity("s", -1));
    activityList.add(getActivity("x", 2));
    var actual = sut.addAll(activityList);
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Test that fails for no id.
   */
  @Test
  public void getAllActivitiesBadRequestCannotHaveEmptyID() {
    List<Activity> activityList = new ArrayList<>();
    activityList.add(getActivity("", 1));
    activityList.add(getActivity("x", 2));
    var actual = sut.addAll(activityList);
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Tests for adding an empty list.
   */
  @Test
  public void addAllEmptyList() {
    List<Activity> activityList = new ArrayList<>();
    var actual = sut.addAll(activityList);
    assertEquals(BAD_REQUEST, actual.getStatusCode());
  }

  /**
   * Tests a successful GET endpoint using an id.
   */
  @Test
  public void getByIdOK() {
    Activity a = new Activity("00", "Test", 100, "test");
    repo.save(a);
    var actual = sut.getById(a.getId());
    assertEquals(OK, actual.getStatusCode());
    assertEquals(a, actual.getBody());
  }

  /**
   * Tests an invalid GET using an id.
   */
  @Test
  public void getByIdFail() {
    Activity a = new Activity("00", "Test", 100, "test");
    repo.save(a);
    var actual = sut.getById("Shower");
    assertEquals(BAD_REQUEST, actual.getStatusCode());
    assertNotEquals(actual.getBody(), a);
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

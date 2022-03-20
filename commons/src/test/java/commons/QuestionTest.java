package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests Question class.
 */
class QuestionTest {


  /**
   * Tests the getText() method. ONLY USED FOR QUESTION SUBTYPES
   */
  @Test
  void getText() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    Question q = new Question(actList);

    assertNull(q.getText());
  }

  /**
   * Tests getId() method.
   */
  @Test
  void getId() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    Question q = new Question(actList);

    q.setId(1L);
    assertEquals(1L, q.getId());
  }

  /**
   * Failing getId() test.
   */
  @Test
  void getIdFail() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    Question q = new Question(actList);
    q.setId(1L);

    assertNotEquals(2L, (long) q.getId());
  }

  /**
   * Tests setId() method.
   */
  @Test
  void setId() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    Question q = new Question(actList);

    q.setId(1L);
    assertEquals(1L, q.getId());
    q.setId(2L);
    assertEquals(2L, q.getId());
  }

  /**
   * Tests getActivities() method.
   */
  @Test
  void getActivities() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    Question q = new Question(actList);

    assertEquals(ac, q.getActivities().get(0));
  }

  /**
   * Tests getActivities().size().
   */
  @Test
  void getActivitiesSize() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    Question q = new Question(actList);

    assertEquals(1, q.getActivities().size());
  }

  /**
   * Tests equals() method.
   */
  @Test
  void testEquals() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    Question q = new Question(actList);
    Object test = new Question(actList);

    assertEquals(test, q);
  }
}
package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    List<Activity> actList = new ArrayList<>();
    Question q = new Question(actList);

    assertNull(q.getText());
  }

  /**
   * Tests getId() method.
   */
  @Test
  void getId() {
    List<Activity> actList = new ArrayList<>();
    Question q = new Question(actList);

    q.setId(1L);
    assertEquals(1L, q.getId());
  }

  /**
   * Tests hashCode() method.
   */
  @Test
  void testHashCode() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    Question q = new Question(actList);

    Activity ac1 = new Activity("s", "x", 1, "x");
    List<Activity> actList1 = new ArrayList<>();
    actList1.add(ac1);
    Question q1 = new Question(actList1);

    assertNotEquals(q, q1);
    assertNotEquals(q.hashCode(), q1.hashCode());
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

  /**
   * Test for toString method.
   */
  @Test
  void toStringTest() {
    Activity a = new Activity("1", "test1", 12, "test");
    List<Activity> acL = new ArrayList<>();
    acL.add(a);
    var actual = new Question(acL).toString();
    assertTrue(actual.contains(Question.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("title"));
  }
}
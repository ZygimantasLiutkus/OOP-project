package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests QuestionMoreExpensive class.
 */
class QuestionMoreExpensiveTest {

  /**
   * Tests constructor.
   */
  @Test
  void constructorTest() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    QuestionMoreExpensive q = new QuestionMoreExpensive(actList);

    assertNotNull(q);
  }

  /**
   * Test for empty constructor.
   */
  @Test
  void constructorEmpty() {
    QuestionMoreExpensive q = new QuestionMoreExpensive();
    assertNotNull(q);
  }

  /**
   * Tests getText() method.
   */
  @Test
  void getText() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    QuestionMoreExpensive q = new QuestionMoreExpensive(actList);

    assertEquals("Which is more expensive?", q.getText());
  }

  /**
   * Fails getText() method test since it is not a QuestionMultipleChoice.
   */
  @Test
  void getTextFail() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    QuestionMoreExpensive q = new QuestionMoreExpensive(actList);

    assertNotEquals(" How big is the consumption per hour for this activity?", q.getText());
  }
}
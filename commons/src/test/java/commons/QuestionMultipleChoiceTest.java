package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests QuestionMultipleChoice class.
 */
class QuestionMultipleChoiceTest {

  /**
   * Tests constructor.
   */
  @Test
  void constructorTest() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    QuestionMultipleChoice q = new QuestionMultipleChoice(actList);

    assertNotNull(q);
  }

  /**
   * Test for empty constructor.
   */
  @Test
  void emptyConstructor() {
    QuestionMultipleChoice q = new QuestionMultipleChoice();
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
    QuestionMultipleChoice q = new QuestionMultipleChoice(actList);

    assertEquals("How big is the consumption per hour for this activity?", q.getText());
  }

  /**
   * Fails getText() method test since it is not a QuestionMoreExpensive.
   */
  @Test
  void getTextFail() {
    Activity ac = new Activity("x", "x", 1, "x");
    List<Activity> actList = new ArrayList<>();
    actList.add(ac);
    QuestionMultipleChoice q = new QuestionMultipleChoice(actList);

    assertNotEquals("Which is more expensive?", q.getText());
  }
}
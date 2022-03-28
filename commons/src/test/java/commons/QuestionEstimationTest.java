package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests QuestionEstimation class.
 */
class QuestionEstimationTest {

  /**
   * Constructor test for the QuestionEstimation class.
   */
  @Test
  public void constructorTest() {
    Activity a = new Activity("A", "A", 1, "A");
    List<Activity> list = new ArrayList<>();
    list.add(a);
    QuestionEstimation q = new QuestionEstimation(list);

    assertNotNull(q);
  }

  /**
   * Test for empty constructor.
   */
  @Test
  void constructorEmpty() {
    QuestionEstimation q = new QuestionEstimation();
    assertNotNull(q);
  }

  /**
   * Tests the getText() method.
   */
  @Test
  public void testGetText() {
    Activity a = new Activity("A", "A", 1, "A");
    List<Activity> list = new ArrayList<>();
    list.add(a);
    QuestionEstimation q = new QuestionEstimation(list);

    assertEquals("How much do you think this activity consumes per hour?", q.getText());
  }

  /**
   * Test if the getText() method does not return an incorrect result.
   */
  @Test
  public void testGetTextFail() {
    Activity a = new Activity("A", "A", 1, "A");
    List<Activity> list = new ArrayList<>();
    list.add(a);
    QuestionEstimation q = new QuestionEstimation(list);

    assertNotEquals("a", q.getText());
  }
}
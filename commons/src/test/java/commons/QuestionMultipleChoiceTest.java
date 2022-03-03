package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * A test class for multiple choice question.
 */
public class QuestionMultipleChoiceTest {

  /**
   * Tests if the constructor works properly.
   */
  @Test
  public void testConstruct() {
    QuestionMultipleChoice q = new QuestionMultipleChoice("text", "15", "10", "17", "http://baeldung.com");
    assertEquals(q.getAnswer2(), "10");
    assertEquals(q.getAnswer3(), "17");
  }

}

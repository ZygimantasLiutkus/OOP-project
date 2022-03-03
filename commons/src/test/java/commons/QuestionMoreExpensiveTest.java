package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for "Which is more expensive" question.
 */
public class QuestionMoreExpensiveTest {

  /**
   * Tests the constructor of the class.
   */
  @Test
  public void testConstruct() {
    QuestionMoreExpensive q = new QuestionMoreExpensive("Test", "answer1", "http://baeldung.com",
        "answer2", "http://baeldung.nl", "answer3", "http://baeldung.ro");
    assertEquals(q.getText(), "Test");
    assertEquals(q.getAnswer(), "answer1");
    assertEquals(q.getImage().toString(), "http://baeldung.com");
    assertEquals(q.getAnswer2(), "answer2");
    assertEquals(q.getImage2().toString(), "http://baeldung.nl");
    assertEquals(q.getAnswer3(), "answer3");
    assertEquals(q.getImage3().toString(), "http://baeldung.ro");
  }


}

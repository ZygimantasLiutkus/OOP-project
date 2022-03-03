package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * A test class for question parent class.
 */
public class QuestionTest {

  /**
   * Tests if the constructor works properly.
   */
  @Test
  public void testConstruct() {
    Question q = new Question("text", "10", "http://Baeldung.com");
    assertEquals(q.getText(), "text");
    assertEquals(q.getAnswer(), "10");
    assertEquals(q.getImage().toString(), "http://Baeldung.com");
  }

  /**
   * Tests if 2 identical questions output the same hash code.
   */
  @Test
  public void testTrueHashCode() {
    Question q = new Question("text", "10", "http://baeldung.com");
    Question q1 = new Question("text", "10", "http://baeldung.com");
    assertEquals(q, q1);
    assertEquals(q.hashCode(), q1.hashCode());
  }

  /**
   * Tests if 2 different questions output different hash codes.
   */
  @Test
  public void testFalseHashCode() {
    Question q = new Question("text", "10", "http://baeldung.com");
    Question q1 = new Question("text", "10", "http://baeldung.ro");
    assertNotEquals(q, q1);
    assertNotEquals(q.hashCode(), q1.hashCode());
  }

  /**
   * Tests if the toString contains every info about the question.
   */
  @Test
  public void hasToString() {
    Question q = new Question("text", "10", "http://baeldung.com");
    String actual = q.toString();
    assertTrue(actual.contains(Question.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("image"));
    assertTrue(actual.contains("answer"));
  }
}

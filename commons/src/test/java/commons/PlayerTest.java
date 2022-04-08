package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Only tested methods that will be used the most.
 */
public class PlayerTest {

  /**
   * Testing the class's constructor.
   */
  @Test
  public void testConstructor() {
    Player p = new Player("A");
    assertEquals(p.getName(), "A");
    assertEquals(p.getScore(), 0);
    assertEquals(p.getSelectedAnswer(), "0");
  }

  /**
   * Test for empty constructor.
   */
  @Test
  void emptyConstructor() {
    Player p = new Player();
    assertNotNull(p);
  }

  /**
   * Test for constructor with id and string.
   */
  @Test
  void playerConstructorTest() {
    Player a = new Player(1L, "answer1");
    assertEquals(1L, a.getId());
    assertEquals("answer1", a.getSelectedAnswer());
  }

  /**
   * Testing to see if 2 players that are the same output the same hash code.
   */
  @Test
  public void testEqualHashCode() {
    Player p = new Player("A");
    Player p1 = new Player("A");
    assertEquals(p, p1);
    assertEquals(p.hashCode(), p1.hashCode());
  }

  /**
   * Testing to see if 2 different players output different hash codes.
   */
  @Test
  public void testNotEqualsHashCode() {
    Player p = new Player("A");
    Player p1 = new Player("B");
    assertNotEquals(p, p1);
    assertNotEquals(p.hashCode(), p1.hashCode());
  }

  /**
   * Testing to see if the string format contains out specified info.
   */
  @Test
  public void hasToString() {
    Player p = new Player("A");
    String actual = p.toString();
    assertTrue(actual.contains(Player.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("name"));
  }

  /**
   * Testing to see if a players score can be updated.
   */
  @Test
  public void changeScoreTest() {
    Player p = new Player("Bob");
    assertEquals(p.getScore(), 0);
    p.setScore(100);
    assertEquals(100, p.getScore());
  }

  /**
   * Test for setName method.
   */
  @Test
  void setNameTest() {
    Player p = new Player("test1");
    assertEquals("test1", p.getName());
    p.setName("test2");
    assertEquals("test2", p.getName());
  }

  /**
   * Test for setSelectedAnswer method.
   */
  @Test
  void setSelectedAnswerTest() {
    Player p = new Player("test1");
    p.setSelectedAnswer("0");
    assertEquals("0", p.getSelectedAnswer());
    p.setSelectedAnswer("2");
    assertEquals("2", p.getSelectedAnswer());
  }

  /**
   * Test for getId method.
   */
  @Test
  void getId() {
    Player p = new Player("test1");
    p.setId(1L);
    assertEquals(1L, p.getId());
  }

  /**
   * Test for setId method.
   */
  @Test
  void setId() {
    Player p = new Player("test1");
    p.setId(1L);
    assertEquals(1L, p.getId());
    p.setId(2L);
    assertEquals(2L, p.getId());
  }

  /**
   * Test for getGameId method.
   */
  @Test
  void getGameId() {
    Player p = new Player("test1");
    p.setGameId(1L);
    assertEquals(1L, p.getGameId());
  }

  /**
   * Test for setGameId method.
   */
  @Test
  void setGameId() {
    Player p = new Player("test1");
    p.setGameId(1L);
    assertEquals(1L, p.getGameId());
    p.setGameId(2L);
    assertEquals(2L, p.getGameId());
  }
}

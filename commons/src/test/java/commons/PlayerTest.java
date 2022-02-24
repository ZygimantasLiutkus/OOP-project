package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    assertEquals(p.getGameSessionId(), null);
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

}

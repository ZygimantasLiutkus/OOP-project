package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Only tested methods that will be used the most.
 */
public class PlayerTest {

  @Test
  public void testConstructor() {
    Player p = new Player("A");
    assertEquals(p.getName(), "A");
    assertEquals(p.getScore(), 0);
    assertEquals(p.getGameSessionId(), null);
  }

  @Test
  public void testEqualHashCode() {
    Player p = new Player("A");
    Player p1 = new Player("A");
    assertEquals(p, p1);
    assertEquals(p.hashCode(), p1.hashCode());
  }

  @Test
  public void testNotEqualsHashCode() {
    Player p = new Player("A");
    Player p1 = new Player("B");
    assertNotEquals(p, p1);
    assertNotEquals(p.hashCode(), p1.hashCode());
  }

  @Test
  public void hasToString() {
    Player p = new Player("A");
    String actual = p.toString();
    assertTrue(actual.contains(Player.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("name"));
  }

  @Test
  public void changeScoreTest() {
    Player p = new Player("Bob");
    assertEquals(p.getScore(), 0);
    p.setScore(100);
    assertEquals(100, p.getScore());
  }

}

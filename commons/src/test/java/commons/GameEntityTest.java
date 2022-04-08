package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the GameEntity class.
 */
class GameEntityTest {

  /**
   * Test to get the type.
   */
  @Test
  void getType() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    assertEquals(GameEntity.Type.MULTIPLAYER, a.getType());
  }

  /**
   * Test to set the type.
   */
  @Test
  void setType() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    a.setType(GameEntity.Type.SINGLEPLAYER);
    assertEquals(GameEntity.Type.SINGLEPLAYER, a.getType());
  }

  /**
   * Test for getStatus method.
   */
  @Test
  void getStatus() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    assertEquals("WAITING", a.getStatus());
  }

  /**
   * Test for setStatus method.
   */
  @Test
  void setStatus() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    a.setStatus("STARTED");
    assertEquals("STARTED", a.getStatus());
  }

  /**
   * Test for getPlayers method.
   */
  @Test
  void getPlayers() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    assertEquals("[]", a.getPlayers().toString());
    Player p = new Player("asd");
    List<Player> listP = new ArrayList<>();
    listP.add(p);
    a.setPlayers(listP);
    assertEquals(listP, a.getPlayers());

  }

  /**
   * Test for setPlayers method.
   */
  @Test
  void setPlayers() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    Player p = new Player("asd");
    List<Player> listP = new ArrayList<>();
    listP.add(p);
    a.setPlayers(listP);
    assertEquals(listP, a.getPlayers());
    Player v = new Player("zxc");
    listP.add(v);
    a.setPlayers(listP);
    assertEquals(listP, a.getPlayers());
  }

  /**
   * Test for getQuestion method.
   */
  @Test
  void getQuestions() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    assertEquals(l, a.getQuestions());
  }

  /**
   * Test for setQuestions method.
   */
  @Test
  void setQuestions() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    assertEquals(l, a.getQuestions());
    List<Question> l1 = new ArrayList<>();
    Question v = new Question();
    l1.add(v);
    a.setQuestions(l1);
    assertEquals(l1, a.getQuestions());
  }

  /**
   * Test for getId method.
   */
  @Test
  void getId() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    a.setId(1L);
    assertEquals(1L, a.getId());
  }

  /**
   * Test for setId method.
   */
  @Test
  void setId() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    a.setId(1L);
    assertEquals(1L, a.getId());
    a.setId(2L);
    assertEquals(2L, a.getId());
  }

  /**
   * Test for equals method.
   */
  @Test
  void testEquals() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);

    Question q1 = new Question();
    List<Question> l1 = new ArrayList<>();
    l1.add(q1);
    Object b = new GameEntity(GameEntity.Type.MULTIPLAYER, l1);
    assertEquals(b, a);
  }

  /**
   * Test for hashCode method.
   */
  @Test
  void testHashCode() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);

    Activity act = new Activity();
    List<Activity> actL = new ArrayList<>();
    actL.add(act);
    Question q1 = new Question(actL);
    List<Question> l1 = new ArrayList<>();
    l1.add(q1);
    Object b = new GameEntity(GameEntity.Type.MULTIPLAYER, l1);

    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
  }

  /**
   * Test for addPlayer method.
   */
  @Test
  void addPlayer() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    Player pl = new Player("asd");
    a.addPlayer(pl);
    assertEquals(pl, a.getPlayers().get(0));
  }

  /**
   * Test for addQuestion method.
   */
  @Test
  void addQuestion() {
    Question q = new Question();
    List<Question> l = new ArrayList<>();
    l.add(q);
    GameEntity a = new GameEntity(GameEntity.Type.MULTIPLAYER, l);
    Question v = new Question();
    a.addQuestion(v);
    assertEquals(v, a.getQuestions().get(1));
  }

  /**
   * Test for constructor.
   */
  @Test
  void constructorTest() {
    GameEntity a = new GameEntity();
    assertNotNull(a);
  }

  /**
   * Test for constructor with status.
   */
  @Test
  void constructorWithStatusTest() {
    GameEntity a = new GameEntity("STARTED");
    assertNotNull(a);
    assertEquals("STARTED", a.getStatus());
  }

  /**
   * Test for toString method.
   */
  @Test
  void toStringTest() {
    var actual = new GameEntity().toString();
    assertTrue(actual.contains(GameEntity.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("WAITING"));
  }
}
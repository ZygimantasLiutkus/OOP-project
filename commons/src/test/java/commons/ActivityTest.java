package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for activity.
 */
public class ActivityTest {

  /**
   * Tests whether the constructor works properly.
   */
  @Test
  public void testConstructor() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    assertEquals(a.getId(), "00-A");
    assertEquals(a.getTitle(), "How");
    assertEquals(a.getConsumption_in_wh(), 123);
    assertEquals(a.getImage_path(), "file/file");
  }

  /**
   * Test whether the setter for the title works.
   */
  @Test
  public void testSetTitle() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    a.setTitle("How much");
    assertEquals(a.getTitle(), "How much");
  }

  /**
   * Test whether the setter for the image path works.
   */
  @Test
  public void testSetImage_path() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    a.setImage_path("file/newFile");
    assertEquals(a.getImage_path(), "file/newFile");
  }

  /**
   * Test whether the setter for consumption works.
   */
  @Test
  public void testSetConsumption_in_wh() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    a.setConsumption_in_wh(456);
    assertEquals(a.getConsumption_in_wh(), 456);
  }

  /**
   * Tests whether identical activities output the same hash code.
   */
  @Test
  public void testHashCodeTrue() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    Activity b = new Activity("00-A", "How", 123, "file/file");
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  /**
   * Tests whether different activities output different hash codes.
   */
  @Test
  public void testHashCodeFalse() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    Activity b = new Activity("00-A", "Which", 120, "file/file");
    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
  }

  /**
   * Tests whether the string format contains every info about an activity.
   */
  @Test
  public void hasToString() {
    Activity a = new Activity("00-A", "How", 123, "file/file");
    String actual = a.toString();
    assertTrue(actual.contains(Activity.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("id"));
    assertTrue(actual.contains("consumption_in_wh"));
  }
}

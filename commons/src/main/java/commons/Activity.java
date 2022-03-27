package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class that holds every info from the activity JSON.
 */
@Entity
public class Activity {
  @Id
  @Column(name = "id", nullable = false)
  public String id;
  private String title;
  private int consumption_in_wh;
  private String image_path;

  /**
   * For object mappers.
   */
  public Activity() {
  }

  /**
   * The constructor for the class.
   *
   * @param id                the activity id
   * @param title             the title
   * @param consumption_in_wh activity's consumption in watt
   * @param path              the file path to the photo
   */
  public Activity(String id, String title, int consumption_in_wh, String path) {
    this.id = id;
    this.title = title;
    this.consumption_in_wh = consumption_in_wh;
    this.image_path = path;
  }

  /**
   * A getter for the title.
   *
   * @return the activities title
   */
  public String getTitle() {
    return title;
  }

  /**
   * A setter for the title.
   *
   * @param title the title of the activity.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * A getter for the path.
   *
   * @return the path to the photo
   */
  public String getImage_path() {
    return image_path;
  }

  /**
   * A setter for the image path.
   *
   * @param image_path the path to the image
   */
  public void setImage_path(String image_path) {
    this.image_path = image_path;
  }

  /**
   * A getter for the consumption.
   *
   * @return an integer representing consumption
   */
  public int getConsumption_in_wh() {
    return consumption_in_wh;
  }

  /**
   * A setter for the consumption.
   *
   * @param consumption_in_wh an integer representing consumption.
   */
  public void setConsumption_in_wh(int consumption_in_wh) {
    this.consumption_in_wh = consumption_in_wh;
  }

  /**
   * Getter for the id.
   *
   * @return a long generated using Identity
   */
  public String getId() {
    return id;
  }

  /**
   * A method that uses an API supportive version of the "equals" method.
   *
   * @param obj a random type of object to be compared to
   * @return a boolean whether these 2 objects are the same or not
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  /**
   * A method that uses an API supportive version of hashing.
   *
   * @return a hash code of the Player object
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * A method that uses an API supportive method of transforming data into a string.
   *
   * @return a string containing every detail about the player
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
  }

}

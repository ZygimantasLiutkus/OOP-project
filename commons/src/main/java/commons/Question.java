package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * A general class that contains identical attributes for every type of question.
 * Needs to change - import activities into question, dont hardcode info into it
 */
@Entity(name = "question")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  private String text;
  @ManyToMany(cascade = CascadeType.PERSIST)
  private List<Activity> activities = new ArrayList<>();

  /**
   * For object mappers.
   */
  @SuppressWarnings("unused")
  public Question() {
  }

  /**
   * The constructor for the class.
   *
   * @param activities the list of activities
   */
  public Question(List<Activity> activities) {
    this.activities = activities;
  }

  /**
   * Abstract method that dictates the question text.
   *
   * @return a predefined question text.
   */
  public String getText() {
    return text;
  }

  /**
   * A getter for the question's id.
   *
   * @return a long generated using Identity
   */
  public Long getId() {
    return id;
  }

  /**
   * A setter where we can change the question's id (not recommendable).
   *
   * @param id a long generated using Identity
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * A getter for the activities list.
   *
   * @return a list of activities
   */
  public List<Activity> getActivities() {
    return activities;
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

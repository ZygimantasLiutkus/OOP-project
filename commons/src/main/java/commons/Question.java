package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A general class that contains identical attributes for every type of question.
 */

public class Question {

  private Long id;
  private String text;
  private String answer;
  private String image;

  /**
   * A constructor for the class.
   *
   * @param text   - the question itself
   * @param answer - the id of a numeric answer from the "Answer" database
   * @param image  -  the file path towards a descriptive image
   */
  public Question(String text, String answer, String image) {
    this.text = text;
    this.answer = answer;
    this.image = image;
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
   * A getter for the question's text.
   *
   * @return a string that represents the question
   */
  public String getText() {
    return text;
  }

  /**
   * A setter for the question's text.
   *
   * @param text - a valid interrogative sentence
   */
  public void setText(String text) {
    if (text != null) {
      this.text = text;
    }
  }

  /**
   * A getter for the answer id.
   *
   * @return a valid id take from the "Answer" database
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * A method where we can change the answer id (not recommendable).
   *
   * @param answer - a long generated using Identity
   */
  public void setAnswer(String answer) {
    if (answer != null) {
      this.answer = answer;
    }
  }

  /**
   * A getter for the image URL.
   *
   * @return a file path corresponding to a location inside the "Images" file
   */
  public String getImage() {
    return image;
  }

  /**
   * A method where we can change the location of the question's photo.
   *
   * @param image - a valid file path inside the "Images" file
   */
  public void setImage(String image) {
    if (image != null) {
      this.image = image;
    }
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

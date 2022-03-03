package commons;

import javax.persistence.*;

/**
 * Question type that compares activities.
 * "Which one is more expensive?"
 */
public class QuestionMoreExpensive extends Question {

  private Long id;
  private String answer2;
  private String image2;
  private String answer3;
  private String image3;

  /**
   * A constructor of the class.
   *
   * @param text    the question itself
   * @param answer  the correct answer
   * @param image   the image of the correct answer
   * @param answer2 an incorrect answer
   * @param image2  the image of the second answer
   * @param answer3 an incorrect answer
   * @param image3  the image of the third answer
   */
  public QuestionMoreExpensive(String text, String answer, String image,
                               String answer2, String image2, String answer3, String image3) {
    super(text, answer, image);
    this.answer2 = answer2;
    this.image2 = image2;
    this.answer3 = answer3;
    this.image3 = image3;
  }

  /**
   * A getter for the second image (incorrect answer).
   *
   * @return a file path towards the image
   */
  public String getAnswer2() {
    return answer2;
  }

  /**
   * A setter for the second answer.
   *
   * @param answer a string representing an activity
   */
  public void setAnswer2(String answer) {
    this.answer2 = answer2;
  }

  /**
   * a getter for the second answer's image.
   *
   * @return a file path towards the image
   */
  public String getImage2() {
    return image2;
  }

  /**
   * A setter for the second image.
   *
   * @param image2 a valid url towards the "Images" folder
   */
  public void setImage2(String image2) {
    this.image2 = image2;
  }

  /**
   * A getter for the third answer (incorrect).
   *
   * @return a string representing an activity
   */
  public String getAnswer3() {
    return answer3;
  }

  /**
   * A setter for the third answer.
   *
   * @param answer3 a string representing an activity
   */
  public void setAnswer3(String answer3) {
    this.answer3 = answer3;
  }

  /**
   * A getter for the third answer's image.
   *
   * @return a valid URL towards an image
   */
  public String getImage3() {
    return image3;
  }

  /**
   * A setter for the third image.
   *
   * @param image3 a valid URL towards the "Images" folder
   */
  public void setImage3(String image3) {
    this.image3 = image3;
  }

  /**
   * A getter for the id.
   *
   * @return a long generated using Identity
   */
  public Long getId() {
    return id;
  }

  /**
   * A setter for the class.
   *
   * @param id a long generated using Identity
   */
  public void setId(Long id) {
    this.id = id;
  }
}

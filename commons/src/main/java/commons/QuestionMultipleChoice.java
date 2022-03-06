package commons;

import java.util.Random;

/**
 * MC questions will have the outline below also for the table with the name specified below.
 */

public class QuestionMultipleChoice extends Question {

  private String answer2;
  private String answer3;

  /**
   * A constructor for the multiple-choice question.
   * Unsure if the constructor should have
   * the incorrect answers or not as defaults.
   *
   * @param text    the question itself
   * @param answer  the correct answer
   * @param answer2 the second answer (incorrect)
   * @param answer3 the third answer (incorrect)
   * @param image   a file path towards a descriptive image
   */
  public QuestionMultipleChoice(String text, String answer,
                                String answer2, String answer3, String  image) {
    super(text, answer, image);
    this.answer2 = answer2;
    this.answer3 = answer3;
  }

  /**
   * A getter for the second answer.
   *
   * @return an integer that was randomly generated
   */
  public String getAnswer2() {
    return answer2;
  }

  /**
   * A getter for the third question.
   *
   * @return an integer that was randomly generated
   */
  public String getAnswer3() {
    return answer3;
  }

  /**
   * A setter for the second answer (to be used when assigning answers).
   *
   * @param answer2 a positive integer
   */
  public void setAnswer2(String answer2) {
    this.answer2 = answer2;
  }

  /**
   * A setter for the third answer (to be used when assigning answers).
   *
   * @param answer3 a positive integer
   */
  public void setAnswer3(String answer3) {
    this.answer3 = answer3;
  }

  /**
   * A method that generates the incorrect answers.
   * These will be 2 close numbers semi-randomized.
   * Aso sets the answers.
   */
  public void createAnswer() {
    int nr = Integer.valueOf(getAnswer());
    int[] percentage = {-25, -15, -10, -5, 5, 10, 15, 20};
    Random random = new Random();
    int op1 = random.nextInt(8);
    int low = nr + percentage[op1] * nr / 100;
    int op2 = random.nextInt(8);
    while (op2 != op1) {
      op2 = random.nextInt(8);
    }
    int high = nr + op2 * nr / 100;
    setAnswer2(Integer.toString(low));
    setAnswer3(Integer.toString(high));
  }
}


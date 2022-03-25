package commons;

import java.util.List;
import javax.persistence.Entity;

/**
 * Question that asks for an energy consumption estimation of an activity.
 */
@Entity(name = "QuestionE")
public class QuestionEstimation extends Question {

  /**
   * For object mappers.
   */
  public QuestionEstimation() {
    super();
  }

  public QuestionEstimation(List<Activity> activities) {
    super(activities);
  }

  /**
   * A getter that displays the question.
   *
   * @return a string containing the appropriate question
   */
  @Override
  public String toString() {
    return "How much do you think this activity consumes per hour?";
  }
}

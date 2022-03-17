package commons;


import java.util.List;
import javax.persistence.*;

/**
 * Question type that compares activities.
 * "Which one is more expensive?"
 */
@Entity
public class QuestionMoreExpensive extends Question {

  /**
   * For object mappers.
   */
  public QuestionMoreExpensive() {
    super();
  }

  /**
   * The class' constructor.
   *
   * @param activities a list of activities
   */
  public QuestionMoreExpensive(List<Activity> activities) {
    super(activities);
  }

  /**
   * A getter that displays a question.
   *
   * @return a string containing an appropriate question
   */
  @Override
  public String getText() {
    return "Which is more expensive?";
  }

}

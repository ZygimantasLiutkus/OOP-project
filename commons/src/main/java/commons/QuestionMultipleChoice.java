package commons;

import java.util.List;
import javax.persistence.Entity;

/**
 * MC questions will have the outline below also for the table with the name specified below.
 */

@Entity(name = "QuestionMc")
public class QuestionMultipleChoice extends Question {

  /**
   * For object mappers.
   */
  public QuestionMultipleChoice() {
    super();
  }

  /**
   * The class' constructor.
   *
   * @param activities the list of activities
   */
  public QuestionMultipleChoice(List<Activity> activities) {
    super(activities);
  }

  /**
   * Displays the appropriate question text.
   *
   * @return a string containing the question
   */
  @Override
  public String getText() {
    return "How big is the consumption per hour for this activity?";
  }

  /**
   * Method to obtain the question type.
   *
   * @return an enum
   */
  public QuestionTypes.QuestionType getType() {
    return QuestionTypes.QuestionType.MUTIPLE_CHOICE;
  }
}


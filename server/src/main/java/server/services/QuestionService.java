package server.services;

import commons.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

/**
 * Class that chooses the content of each question.
 */
@Service
public class QuestionService {

  private final ActivityRepository repo;
  private final Random random;

  /**
   * The class' constructor.
   *
   * @param repo   the activity repository
   * @param random a random that will help choose activities
   */
  public QuestionService(ActivityRepository repo, Random random) {
    this.repo = repo;
    this.random = random;
  }

  /**
   * Method that creates a poll of questions.
   *
   * @param amount the amount of questions that needs to be created
   * @return a list of different types of question
   */
  public List<Question> generateQuestion(int amount) {
    if (amount * 3L > repo.count()) {
      return new ArrayList<Question>();
    }
    List<Question> list = new ArrayList<>();
    List<Integer> used = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      list.add(make(used));
    }
    return list;
  }

  /**
   * Method that generates a question.
   * Will choose a random number to see what type of question to add.
   *
   * @param used the list of used ids
   * @return a random type of question.
   */
  public Question make(List<Integer> used) {
    int nr = random.nextInt(3);
    switch (nr) {
      case 0:
        return new QuestionMultipleChoice(getActivities(3, used));
      case 1:
        return new QuestionMoreExpensive(getActivities(3, used));
      case 2:
        return new QuestionEstimation(getActivities(1, used));
      default:
        return null;
    }
  }

  /**
   * Method that generates a number of activities to create a question.
   * Each activity is only chosen once.
   *
   * @param n    the number of wanted activities
   * @param used the list of all used ids
   * @return a list of n activities
   */
  public List<Activity> getActivities(int n, List<Integer> used) {
    List<Activity> list = new ArrayList<>();
    List<Activity> all = repo.findAll();
    for (int i = 0; i < n; i++) {
      int nr = random.nextInt(all.size());
      while (used.contains(nr)) {
        nr = random.nextInt(all.size());
      }
      Activity x = all.get(nr);
      used.add(nr);
      list.add(x);
    }
    return list;
  }
}

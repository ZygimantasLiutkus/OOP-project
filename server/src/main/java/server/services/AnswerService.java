package server.services;

import commons.Activity;
import commons.Answer;
import commons.GameEntity;
import commons.Player;
import commons.Question;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.GameEntityRepository;
import server.database.PlayerRepository;

/**
 * Class to process the selected answer,
 * compute the score and generate an answer containing feedback.
 */
public class AnswerService {

  private GameEntityRepository repo;
  private PlayerRepository playerRepo;

  /**
   * The constructor of the AnswerService.
   *
   * @param repo       repository of games
   * @param playerRepo repository of players
   */
  public AnswerService(GameEntityRepository repo, PlayerRepository playerRepo) {
    this.repo = repo;
    this.playerRepo = playerRepo;
  }

  /**
   * Updates the score of a player.
   *
   * @param gameID the id of the game
   * @param player the player for which the score needs to be updated
   * @return answer
   */
  public ResponseEntity updateScore(long gameID, Player player) {
    if (!repo.existsById(gameID)) {
      return ResponseEntity.badRequest().build();
    }

    GameEntity game = repo.getById(gameID);
    if (!game.getStatus().equals("STARTED")) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    playerRepo.save(player);
    return ResponseEntity.ok().build();
  }

  /**
   * A method to find if a player is in a specific game.
   *
   * @param game   a specific game
   * @param player a player that is expected to be found in the game
   * @return the player if they are in the game, null if they are not
   */
  public Player findPlayer(GameEntity game, Player player) {
    for (Player p : game.getPlayers()) {
      if (Objects.equals(p.getId(), player.getId())) {
        return p;
      }
    }
    return null;
  }

  /**
   * A method to find a game.
   *
   * @param id id of the game
   * @return the game if it is found, or a specific error
   */
  public ResponseEntity<GameEntity> findGame(long id) {
    for (GameEntity game : repo.findAll()) {
      if (game.getId() == id) {
        if (!game.getStatus().equals("STARTED")) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(game);
      }
    }
    return ResponseEntity.badRequest().build();
  }

  /**
   * A method that computes the outcome of answering a more expensive question.
   *
   * @param q      the question being answered
   * @param player the player answering the question
   * @return answer containing feedback on the submission.
   */
  public ResponseEntity<Answer> answerME(Question q, Player player) {
    int max = 0;
    for (Activity a : q.getActivities()) {
      if (a.getConsumption_in_wh() > max) {
        max = a.getConsumption_in_wh();
      }
    }

    if (Integer.parseInt(player.getSelectedAnswer()) == max) {
      player.setScore(player.getScore() + 100);
      playerRepo.save(player);
      return ResponseEntity.ok(
          new Answer("CORRECT", player, player.getScore(), 100));
    } else {
      playerRepo.save(player);
      return ResponseEntity.ok(
          new Answer("INCORRECT", player, player.getScore(), 0));
    }
  }

  /**
   * A method that computes the outcome of answering a multiple choice question.
   *
   * @param q      the question being answered
   * @param player the player answering the question
   * @return answer containing feedback on the submission.
   */
  public ResponseEntity<Answer> answerMC(Question q, Player player) {
    if (Integer.parseInt(player.getSelectedAnswer())
        == q.getActivities().get(0).getConsumption_in_wh()) {
      player.setScore(player.getScore() + 100);
      playerRepo.save(player);
      return ResponseEntity.ok(new Answer("CORRECT", player, player.getScore(), 100));
    } else {
      playerRepo.save(player);
      return ResponseEntity.ok(new Answer("INCORRECT", player, player.getScore(), 0));
    }
  }

  /**
   * A method that computes the outcome of answering an estimation question.
   *
   * @param q      the question being answered
   * @param player the player answering the question
   * @return answer containing feedback on the submission
   */
  public ResponseEntity<Answer> answerE(Question q, Player player) {
    double answer = (double) q.getActivities().get(0).getConsumption_in_wh();
    double actual = Double.parseDouble(player.getSelectedAnswer());
    double p = Math.abs(answer - actual) / answer;
    int points = (int) (100 * (1 - p / 0.2));

    if (points > 0) {
      player.setScore(player.getScore() + points);
      playerRepo.save(player);
      return ResponseEntity.ok(
          new Answer("CORRECT", player, player.getScore(), points));
    } else {
      playerRepo.save(player);
      return ResponseEntity.ok(
          new Answer("INCORRECT", player, player.getScore(), points));
    }
  }

  /**
   * An enum for different question types.
   */
  public enum QType {
    QuestionEstimation,
    QuestionMoreExpensive,
    QuestionMultipleChoice
  }
}

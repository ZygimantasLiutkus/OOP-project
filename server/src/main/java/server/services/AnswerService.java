package server.services;

import commons.GameEntity;
import commons.Player;
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
  public ResponseEntity<Player> updateScore(long gameID, Player player) {
    if (!repo.existsById(gameID)) {
      return ResponseEntity.badRequest().build();
    }

    GameEntity game = repo.getById(gameID);
    if (!game.getStatus().equals("STARTED")) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(playerRepo.save(player));
  }
}

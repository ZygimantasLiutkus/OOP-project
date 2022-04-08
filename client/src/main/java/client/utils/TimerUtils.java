package client.utils;

import client.scenes.MainCtrl;
import client.scenes.QuestionGameCtrl;
import commons.GameEntity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Class to set up timers needed in the game.
 */
public class TimerUtils {

  /**
   * Sets up the timeline for the progress bar.
   *
   * @param controller the game controller
   * @return the created timeline
   */
  public Timeline setupTimeline(QuestionGameCtrl controller) {
    Timeline timeline = new Timeline();
    timeline.setCycleCount(1000);
    timeline.setAutoReverse(false);
    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15),
        new EventHandler<>() {
          /**
           * {@inheritDoc}
           */
          @Override
          public void handle(ActionEvent event) {
            double progress = controller.getProgress();
            if (progress >= 0.001) {
              progress -= 0.001;
              controller.setProgress(progress);
            }
          }
        }));
    timeline.setOnFinished(e -> controller.revealAnswer());
    return timeline;
  }

  /**
   * Sets up the timeline for the counter.
   *
   * @param controller the game controller
   * @return the created timeline
   */
  public Timeline setupCounter(QuestionGameCtrl controller) {
    Timeline timeCount = new Timeline(
        new KeyFrame(Duration.seconds(1), e -> {
          int startTime = controller.getStartTime() - 1;
          controller.updateCounter(startTime);
        })
    );
    timeCount.setCycleCount(15);
    return timeCount;
  }

  /**
   * Sets up the timeline for the cooldown.
   *
   * @param controller the game controller
   * @return the created timeline
   */
  public Timeline setupCooldown(QuestionGameCtrl controller) {
    Timeline cooldown = new Timeline();
    cooldown.getKeyFrames().add(new KeyFrame(Duration.millis(3000), e -> {
    }));
    cooldown.setOnFinished(e -> {
      controller.cooldownAnswer();
    });
    return cooldown;
  }

  /**
   * Sets up cooldown for intermediate table.
   *
   * @param controller the game controller.
   * @param mainCtrl   the main game controller.
   * @return the created timeline.
   */
  public Timeline intermediateTable(QuestionGameCtrl controller, MainCtrl mainCtrl) {
    Timeline interTime = new Timeline();
    interTime.getKeyFrames().add(new KeyFrame(Duration.millis(10000), e -> {
    }));
    interTime.setOnFinished(e -> {
      mainCtrl.showQuestionGame(GameEntity.Type.MULTIPLAYER);
      controller.nextQuestion();
    });
    return interTime;
  }
}

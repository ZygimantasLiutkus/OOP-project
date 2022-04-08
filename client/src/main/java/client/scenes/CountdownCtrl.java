package client.scenes;

import com.google.inject.Inject;
import commons.GameEntity;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The controller for the countdown screen.
 */
public class CountdownCtrl {

  private final MainCtrl mainCtrl;
  private final QuestionGameCtrl questionGameCtrl;

  @FXML
  public Text count;

  /**
   * Constructor of CountdownCtrl.
   *
   * @param mainCtrl         reference to the main controller.
   * @param questionGameCtrl reference to the question controller.
   */
  @Inject
  public CountdownCtrl(MainCtrl mainCtrl, QuestionGameCtrl questionGameCtrl) {
    this.mainCtrl = mainCtrl;
    this.questionGameCtrl = questionGameCtrl;
  }

  /**
   * Animations for countdown screen.
   *
   * @param type the type of game.
   */
  public void animate(GameEntity.Type type) {
    count.setFill(Color.web("#FCEA82"));

    Animation blink1 = new Transition() {
      {
        setCycleDuration(Duration.millis(2000));
        setInterpolator(Interpolator.EASE_OUT);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double v) {
        Color color = Color.web("#FCEA82", 1.0 - v);
        count.setText("Get Ready!");
        count.setFill(color);
      }
    };

    Animation blink2 = new Transition() {
      {
        setCycleDuration(Duration.millis(1000));
        setInterpolator(Interpolator.EASE_BOTH);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double v) {
        Color color = Color.web("#FCEA82", Math.abs(Math.abs(-1.0 + 2 * v) - 1.0));
        count.setText("3!");
        count.setFill(color);
      }
    };

    Animation blink3 = new Transition() {
      {
        setCycleDuration(Duration.millis(1000));
        setInterpolator(Interpolator.EASE_BOTH);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double v) {
        Color color = Color.web("#FCEA82", Math.abs(Math.abs(-1.0 + 2 * v) - 1.0));
        count.setText("2!");
        count.setFill(color);
      }
    };

    Animation blink4 = new Transition() {
      {
        setCycleDuration(Duration.millis(1000));
        setInterpolator(Interpolator.EASE_BOTH);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double v) {
        Color color = Color.web("#FCEA82", Math.abs(Math.abs(-1.0 + 2 * v) - 1.0));
        count.setText("1!");
        count.setFill(color);
      }
    };

    Animation blink5 = new Transition() {
      {
        setCycleDuration(Duration.millis(1000));
        setInterpolator(Interpolator.EASE_BOTH);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void interpolate(double v) {
        Color color = Color.web("#11AD31", Math.abs(Math.abs(-1.0 + 2 * v) - 1.0));
        count.setText("GO!");
        count.setFill(color);
      }
    };


    SequentialTransition sq = new SequentialTransition(blink1, blink2, blink3, blink4, blink5);

    sq.setOnFinished(new EventHandler<ActionEvent>() {
      /**
       *{@inheritDoc}
       */
      @Override
      public void handle(ActionEvent event) {
        mainCtrl.showQuestionGame(type);
        questionGameCtrl.startGame();
      }
    });

    sq.play();
  }
}

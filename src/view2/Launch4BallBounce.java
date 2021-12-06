package view2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Launch4BallBounce extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        {
            // 创建一个漂亮的小球。
            final int ballRadius = 24;
            Circle ball = new Circle(ballRadius);
            ball.setCenterX(350);
            ball.setCenterY(350);

            final int[] velocityX = new int[] {32};
            final int[] velocityY = new int[] {21};

            // 创建一个球桌。
            final Group ballTable = new Group(ball);

            // 给小球设置图案
            ball.setFill(Color.BLACK);

            ball.setStroke(Color.GREEN);
            ball.setStrokeWidth(3);

            Path ballPath = new Path();

            final Random random = new Random(47);
            MoveTo o = new MoveTo(random.nextInt(700), random.nextInt(700));
            ballPath.getElements().add(o);

            Timeline checkingVelocity = new Timeline();

            PathTransition transition = new PathTransition();

            checkingVelocity.getKeyFrames().add(new KeyFrame(Duration.millis(2000), (e) -> {
                if (ball.getCenterX() + ball.getRadius() > 700)
                    velocityX[0] = -velocityX[0];
                if (ball.getCenterX() - ball.getRadius() < 0)
                    velocityX[0] = -velocityX[0];
                if (ball.getCenterY() + ball.getRadius() > 700)
                    velocityY[0] = - velocityY[0];
                if (ball.getCenterY() - ball.getRadius() < 0)
                    velocityY[0] = - velocityY[0];
//                ballPath.getElements().add(new LineTo(velocityX[0], velocityY[0]));
            }));
            checkingVelocity.setCycleCount(Animation.INDEFINITE);


            for (int i = 0; i < 300; i++) {
                ballPath.getElements().add(new LineTo(random.nextInt(700), random.nextInt(700)));
            }
//            System.out.println(ballPath.getElements());
            transition.setPath(ballPath);
            transition.setDuration(Duration.millis(200000));
            transition.setNode(ball);
            transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(false);

            primaryStage.setScene(new Scene(ballTable, 700, 700));
            primaryStage.setTitle("Path transition example");
            primaryStage.show();

            transition.play();
            checkingVelocity.play();
        }
    }
}

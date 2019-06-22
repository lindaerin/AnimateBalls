package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Animated Balls");
        Canvas canvas = new Canvas(500, 500);
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();
        GraphicsContext graphics = canvas.getGraphicsContext2D();

        Thread thread = new Thread(() -> animate(graphics));
        thread.start();
    }

    private void animate(GraphicsContext gc) {
        Random rand = new Random();

        Ball[] balls = new Ball[30];

        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball(100, 50, Math.random() * 5, Math.random() * 5, 20, Color.BISQUE);
        }

        while (true)
        {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(0, 0, 600, 600);

            for (Ball ball : balls) {
                ball.draw(gc);

            }

            for (Ball ball : balls) {
                Color current = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

                ball.moveOneStep();
                if (ball.getX() <= 0 || ball.getX() >= 500 - ball.getSize()) {
                    ball.bounceX();
                    ball.setColor(current);

                }
                if (ball.getY() <= 0 || ball.getY() >= 500 - ball.getSize()) {
                    ball.bounceY();
                    ball.setColor(current);

                }
            }

            pause(12);
        }

    }

    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package main.java.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.core.ImageBuffer;

public class RayTracerApp extends Application {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    @Override
    public void start(Stage stage) {
        ImageBuffer buffer = new ImageBuffer(WIDTH, HEIGHT);

        // quick test: gradient
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                buffer.setPixel(
                    x, y,
                    (double) x / WIDTH,
                    (double) y / HEIGHT,
                    0.2
                );
            }
        }

        ImageView imageView = new ImageView(buffer.getImage());

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root);

        stage.setTitle("JavaFX Ray Tracer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

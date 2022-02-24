package app;

import core.Engine;
import core.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import samples.GameOfLife;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Game game = new GameOfLife();
        Engine engine = new Engine();
        engine.setGame(game);

        Scene scene = new Scene(new VBox(new ImageView(engine.getOutput())));
        scene.addEventHandler(MouseEvent.ANY, engine.getMouseEventHandler());
        scene.addEventHandler(KeyEvent.ANY, engine.getKeyboardEventHandler());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        engine.start();
    }
}
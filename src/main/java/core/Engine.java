package core;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.nio.IntBuffer;

public class Engine implements Runnable {

    public final int WIDTH = 1024;
    public final int HEIGHT = 768;
    private final int FPS = 240;
    private final PixelBuffer<IntBuffer> pixelBuffer;
    private final Image output;
    private final Renderer renderer;
    private final MouseInput mouseInput;
    private final KeyboardInput keyboardInput;
    private Game game;
    private boolean running = false;

    public Engine() {
        IntBuffer intBuffer = IntBuffer.allocate(WIDTH * HEIGHT);
        pixelBuffer = new PixelBuffer<>(WIDTH, HEIGHT, intBuffer, PixelFormat.getIntArgbPreInstance());
        output = new WritableImage(pixelBuffer);
        renderer = new Renderer(WIDTH, HEIGHT, intBuffer.array());
        mouseInput = new MouseInput();
        keyboardInput = new KeyboardInput();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        running = true;
    }

    public void setGame(Game game) {
        this.game = game;
        this.game.setKeyboardInput(keyboardInput);
        this.game.setMouseInput(mouseInput);
        this.game.setRenderer(renderer);
    }

    public EventHandler<MouseEvent> getMouseEventHandler() {
        return mouseInput;
    }

    public EventHandler<KeyEvent> getKeyboardEventHandler() {
        return keyboardInput;
    }

    public Image getOutput() {
        return output;
    }

    private void updateOutput() {
        pixelBuffer.updateBuffer(intBufferPixelBuffer -> null);
    }

    public void run() {
        long startTimeMillis = System.currentTimeMillis();
        long startTime = System.nanoTime();
        long skipTime = 1000000000 / FPS;
        long sleepTime = 0;
        long frames = 0;

        while (running) {
            long currentTime = System.nanoTime();
            sleepTime += (currentTime - startTime);
            startTime = currentTime;

            if (sleepTime >= skipTime) {
                keyboardInput.update();
                mouseInput.update();
                game.update(sleepTime);
                game.render();

                sleepTime -= skipTime;
                frames++;

                Platform.runLater(this::updateOutput);
            }

            if (System.currentTimeMillis() - startTimeMillis >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                startTimeMillis += 1000;
            }
        }

        stop();
    }

    public void stop() {
        running = false;
    }
}
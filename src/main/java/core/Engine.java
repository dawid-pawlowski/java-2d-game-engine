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

    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    private final int FPS = 60;
    private final PixelBuffer<IntBuffer> pixelBuffer;
    private final Image output;
    private final Renderer renderer;
    private final MouseInput mouseInput;
    private final KeyboardInput keyboardInput;
    private Game game;

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
        long startTime = System.currentTimeMillis();
        long skipTime = 1000L / FPS;
        long sleepTime;
        long frameTime = 0;
        long frames = 0;
        long fps = 0;

        while (true) {
            keyboardInput.update();
            mouseInput.update();

            game.update();
            game.render();

            startTime += skipTime;
            sleepTime = startTime - System.currentTimeMillis();
            frameTime += sleepTime;

            if (frameTime >= 1000) {
                frameTime = 0;
                fps = frames;
                frames = 0;
                //System.out.println(fps);
            }

            if (sleepTime >= 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignored) {
                }
            }

            frames++;

            Platform.runLater(this::updateOutput);
        }
    }
}
package core;

public interface Game {
    void setRenderer(Renderer renderer);

    void setMouseInput(MouseInput mouseInput);

    void setKeyboardInput(KeyboardInput keyboardInput);

    void render();

    void update();
}

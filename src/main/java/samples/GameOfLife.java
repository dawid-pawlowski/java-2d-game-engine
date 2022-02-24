package samples;

import core.KeyboardInput;
import core.MouseInput;
import core.Renderer;
import core.Game;

public class GameOfLife implements Game {

    private Renderer renderer;
    private MouseInput mouseInput;
    private KeyboardInput keyboardInput;


    @Override
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void setMouseInput(MouseInput mouseInput) {
        this.mouseInput = mouseInput;
    }

    @Override
    public void setKeyboardInput(KeyboardInput keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    @Override
    public void render() {
        renderer.drawRect(50, 50, 200, 100);
    }

    @Override
    public void update() {
        renderer.setFillColor(0xFF00FF00);
    }
}

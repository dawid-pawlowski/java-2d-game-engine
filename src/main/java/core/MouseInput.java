package core;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInput extends Input implements EventHandler<MouseEvent> {

    private double mouseX;
    private double mouseY;

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
            keys.put(mouseEvent.getButton(), true);
        }

        if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
            keys.put(mouseEvent.getButton(), false);
        }

        if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        }
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}

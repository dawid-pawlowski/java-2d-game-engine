package core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardInput extends Input implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            keys.put(keyEvent.getCode(), true);
        }

        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            keys.put(keyEvent.getCode(), false);
        }
    }
}

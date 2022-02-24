package core;

import java.util.HashMap;
import java.util.Map;

public class Input {
    protected Map<Object, Boolean> keys = new HashMap<>();
    protected Map<Object, Boolean> previousKeys = new HashMap<>();

    public boolean isKeyUp(Object key) {
        return !keys.getOrDefault(key, false) && previousKeys.getOrDefault(key, false);
    }

    public boolean isKeyDown(Object key) {
        return keys.getOrDefault(key, false) && !previousKeys.getOrDefault(key, false);
    }

    public boolean isKey(Object key) {
        return keys.getOrDefault(key, false);
    }

    public void update() {
        previousKeys.putAll(keys);
    }
}

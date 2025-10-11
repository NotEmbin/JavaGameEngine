package embinmc.javaengine.control;

import com.raylib.Raylib;

public class KeyBind {
    private final int defaultKey;
    private int currentKey;

    public KeyBind(int defaultKey) {
        this.defaultKey = defaultKey;
        this.currentKey = defaultKey;
    }

    public int getCurrentKey() {
        return this.currentKey;
    }

    public boolean isKeyPressed() {
        return Raylib.IsKeyPressed(this.currentKey);
    }

    public boolean isKeyDown() {
        return Raylib.IsKeyDown(this.currentKey);
    }

    public int setNewKeybind(int key) {
        this.currentKey = key;
        return this.currentKey;
    }

    public void resetKeybind() {
        this.currentKey = this.defaultKey;
    }
}

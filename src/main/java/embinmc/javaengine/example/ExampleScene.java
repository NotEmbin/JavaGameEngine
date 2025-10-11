package embinmc.javaengine.example;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.game.scene.Scene;

public class ExampleScene extends Scene {
    @Override
    public void init() {
    }

    @Override
    public void render() {
        Raylib.DrawText("test", 24, 24, 32, Colors.MAGENTA);
    }
}

package embinmc.javaengine.example;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.game.scene.PauseableScene;
import embinmc.javaengine.game.scene.Scene;
import embinmc.javaengine.math.Vec2;

public class ExampleScene extends Scene implements PauseableScene {

    @Override
    public void init() {
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        Raylib.DrawText(String.format("Tick: %s", this.ticks), 24, 128, 24, Colors.MAGENTA);
    }

    @Override
    public void onReplacedOrRemoved() {
    }
}

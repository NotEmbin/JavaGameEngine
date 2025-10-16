package embinmc.javaengine.example;

import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.Game;
import embinmc.javaengine.game.GameArguments;
import embinmc.javaengine.math.Vec2f;
import embinmc.javaengine.util.Color;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

import java.util.Arrays;

public class ExampleGame extends Game {
    protected ExampleGame(GameInitializer initializer) {
        super(GameArguments.createInstance("game_example.json"), initializer, new ExampleScene());
    }

    public static void main(String[] args) {
        Util.getLogger().info("Passed args: {}", Arrays.stream(args).toList());
        Util.getLogger().info(Raylib.GetWorkingDirectory().getString());
        if (Util.probablyBuilt()) {
            Raylib.ChangeDirectory(Util.removeEndFromString(Raylib.GetWorkingDirectory().getString(), "\\bin"));
        }
        Raylib.SetConfigFlags(Raylib.FLAG_WINDOW_TRANSPARENT); // this is crazy, man
        Engine engine = Engine.createInstance(new ExampleGame(ExampleGame::initGame));
        engine.getGame().setLanguageData("assets/examplegame/lang/en_us.json");
        engine.getGame().initAndRunGame();
    }

    public static void initGame(Game game) {
        ExampleKeyBinds.init();
        //Logger logger = Util.getLogger();
    }

    @Override
    public void update() {
        super.update();
        if (ExampleKeyBinds.MOVE_CAM_LEFT.isKeyDown()) {
            this.camera.offset(Vec2f.of(this.camera.offset().x() - 1, this.camera.offset().y()).toRayVec());
        }
        if (ExampleKeyBinds.MOVE_CAM_RIGHT.isKeyDown()) {
            this.camera.offset(Vec2f.of(this.camera.offset().x() + 1, this.camera.offset().y()).toRayVec());
        }
        if (ExampleKeyBinds.MOVE_CAM_UP.isKeyDown()) {
            this.camera.offset(Vec2f.of(this.camera.offset().x(), this.camera.offset().y() - 1).toRayVec());
        }
        if (ExampleKeyBinds.MOVE_CAM_DOWN.isKeyDown()) {
            this.camera.offset(Vec2f.of(this.camera.offset().x() + 1, this.camera.offset().y() + 1).toRayVec());
        }
    }
}

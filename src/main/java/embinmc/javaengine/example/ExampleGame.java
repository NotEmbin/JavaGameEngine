package embinmc.javaengine.example;

import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.Game;
import embinmc.javaengine.game.GameArguments;
import embinmc.javaengine.render.TextureManager;

import java.util.function.Function;
import static com.raylib.Colors.WHITE;

public class ExampleGame extends Game {
    protected ExampleGame(Function<Game, Boolean> initializer) {
        super(GameArguments.createInstance("game_example.json"), initializer, new ExampleScene());
    }

    public static void main(String[] args) {
        Engine.createInstance(new ExampleGame(ExampleGame::initGame));
        Engine.getInstance().getGame().setLanguageData("assets/examplegame/lang/en_us.json");
        Engine.getInstance().getGame().initAndRunGame();
    }

    public static boolean initGame(Game game) {
        ExampleKeyBinds.init();
        // do stuff here like create registries
        return true;
    }

    @Override
    public void render() {
        Raylib.DrawTexture(TextureManager.getManager().missingnoTexture.texture, 64, 64, WHITE);
    }

    @Override
    public void update() {

    }
}

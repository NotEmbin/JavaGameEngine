package embinmc.javaengine;

import embinmc.javaengine.game.Game;
import embinmc.javaengine.game.GameArguments;
import embinmc.javaengine.game.VersionType;
import embinmc.javaengine.registry.EngineRegistries;
import embinmc.javaengine.render.TextureManager;

public class Engine {
    private static Engine INSTANCE;
    private final GameArguments gameArguments;
    private final Game game;

    private Engine(Game game) {
        this.gameArguments = new GameArguments("Java Engine (main)", "engine", "1.0.251014", 1, 60, VersionType.RELEASE);
        this.game = game;
    }

    public static Engine createInstance(Game game) {
        assert Engine.INSTANCE == null : "Cannot create multiple engine instances!";
        //if (Engine.INSTANCE != null) throw new RuntimeException("Cannot create multiple engine instances!");
        Engine.INSTANCE = new Engine(game);
        return Engine.INSTANCE;
    }

    public static Engine getInstance() {
        assert Engine.INSTANCE != null : "Can't get engine instance before it's creation!";
        //if (Engine.INSTANCE == null) throw new RuntimeException("Can't get engine instance before it's creation!");
        return Engine.INSTANCE;
    }

    public String getVersion() {
        return this.gameArguments.versionString();
    }

    public int getDataVersion() {
        return this.gameArguments.dataVersion();
    }

    public String getNamespace() {
        return this.gameArguments.defaultNamespace();
    }

    public GameArguments getGameArguments() {
        return this.gameArguments;
    }

    public Game getGame() {
        return this.game;
    }

    public TextureManager getTextureManager() {
        return this.game.textureManager;
    }

    public void engineInit() {
        EngineRegistries.init();
    }
}

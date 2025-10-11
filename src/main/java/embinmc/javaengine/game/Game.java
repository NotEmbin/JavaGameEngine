package embinmc.javaengine.game;

import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.example.ExampleKeyBinds;
import embinmc.javaengine.game.scene.DefaultPauseScene;
import embinmc.javaengine.game.scene.Scene;
import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.render.TextureManager;
import embinmc.javaengine.resource.Language;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

import java.util.function.Function;

import static com.raylib.Colors.BLANK;

public abstract class Game {
    public Logger logger = Util.getLogger();
    public final GameArguments gameArguments;
    protected final Function<Game, Boolean> initializer; // why static
    public Raylib.Camera2D camera;
    public final Scene defaultScene;
    public Scene currentScene = null;
    public TextureManager textureManager;
    public Scene pauseScene;
    private Language language;

    protected Game(GameArguments gameArguments, Function<Game, Boolean> initializer, Scene defaultScene) {
        this.gameArguments = gameArguments;
        this.initializer = initializer;
        this.defaultScene = defaultScene;
        this.pauseScene = new DefaultPauseScene();
    }

    public void initAndRunGame() {
        this.logger.info("Using engine version {}", Engine.getInstance().getVersion());
        this.logger.info("Loading game: {} ({})", this.gameArguments.gameName(), this.gameArguments.defaultNamespace());

        // raylib stuffs
        String windowTitle = String.format("%s", this.gameArguments.gameName(), this.gameArguments.versionString());
        Raylib.InitWindow(960, 540, windowTitle);
        Raylib.InitAudioDevice();
        // TODO: define "min target fps" var in GameArguments class
        if (this.gameArguments.targetFps() < 10) throw new RuntimeException("Attempting to set FPS target too low!");
        Raylib.SetTargetFPS(this.gameArguments.targetFps());
        this.camera = new Raylib.Camera2D();

        // engine and game stuffs
        Identifier.setDefaultNamespace(this.gameArguments.defaultNamespace());
        Engine.getInstance().engineInit();
        this.textureManager = TextureManager.getManager();
        if (!this.initializer.apply(this)) throw new RuntimeException("Failed to initialize game!");

        for (Identifier id : Registry.ROOT.getEntries().keySet()) {
            Registry.ROOT.getEntryFromId(id).getInit().execute();
            Registry.ROOT.getEntryFromId(id).loadTags();
        }

        this.currentScene = this.defaultScene;
        while (!Raylib.WindowShouldClose()) {
            Raylib.BeginDrawing();
            Raylib.ClearBackground(BLANK);
            this.update();
            this.render();
            if (this.currentScene != null) {
                this.currentScene.update();
                this.currentScene.render();
            }
            if (EngineKeyBinds.PAUSE.isKeyPressed()) {
                this.logger.info("id: {}", Identifier.from("blah"));
            }
            Raylib.DrawFPS(4, 4);
            Raylib.EndDrawing();
        }
        Raylib.CloseAudioDevice();
        Raylib.CloseWindow();
    }

    public abstract void render();
    public abstract void update();

    public void overridePauseScene(Scene scene) {
        this.pauseScene = scene;
    }

    public void setLanguageData(String file) {
        this.language = new Language(file);
    }

    public Language getLanguage() {
        return this.language;
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
}

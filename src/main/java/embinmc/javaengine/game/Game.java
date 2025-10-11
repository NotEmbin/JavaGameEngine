package embinmc.javaengine.game;

import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.game.scene.DefaultPauseScene;
import embinmc.javaengine.game.scene.PauseableScene;
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
    protected Scene pauseScene;
    protected Language language;
    private boolean isPaused;
    protected int ticks;

    protected Game(GameArguments gameArguments, Function<Game, Boolean> initializer, Scene defaultScene) {
        this.gameArguments = gameArguments;
        this.initializer = initializer;
        this.defaultScene = defaultScene;
        this.pauseScene = new DefaultPauseScene();
        this.ticks = 0;
        this.isPaused = false;
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
        this.currentScene.init();
        Raylib.SetExitKey(EngineKeyBinds.CLOSE_GAME.getCurrentKey());

        while (!Raylib.WindowShouldClose()) {
            Raylib.BeginDrawing();
            Raylib.ClearBackground(BLANK);
            this.update();
            this.render();
            if (!this.isPaused) this.currentScene.update();
            this.currentScene.render();
            if (this.currentScene instanceof PauseableScene pauseableScene && EngineKeyBinds.PAUSE.isKeyPressed()) {
                if (!this.isPaused) {
                    pauseableScene.onPause();
                    this.pauseScene.init();
                } else {
                    pauseableScene.onUnpause();
                    this.pauseScene.onReplacedOrRemoved();
                }
                this.isPaused = !this.isPaused;
            }
            if (this.isPaused) {
                this.pauseScene.update();
                this.pauseScene.render();
            }
            Raylib.DrawFPS(4, 4);
            Raylib.EndDrawing();
        }
        this.currentScene.onReplacedOrRemoved();
        if (this.isPaused) this.pauseScene.onReplacedOrRemoved();
        Raylib.CloseAudioDevice();
        Raylib.CloseWindow();
    }

    public abstract void render();
    public void update() {
        this.ticks++;
    }

    public void changeScene(Scene scene) {
        this.currentScene.onReplacedOrRemoved();
        this.currentScene = scene;
        this.currentScene.init();
    }

    public void overridePauseScene(Scene scene) {
        this.pauseScene = scene;
    }

    public Scene getPauseScene() {
        return this.pauseScene;
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

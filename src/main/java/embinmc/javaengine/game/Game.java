package embinmc.javaengine.game;

import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.game.scene.*;
import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.render.TextureManager;
import embinmc.javaengine.resource.Language;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

import static com.raylib.Colors.BLANK;

public abstract class Game {
    public Logger logger;
    public final GameArguments gameArguments;
    protected final GameInitializer initializer; // why static
    public Raylib.Camera2D camera;
    public final Scene defaultScene;
    public Scene currentScene = null;
    public TextureManager textureManager;
    protected Scene pauseScene;
    protected Language language;
    private boolean isPaused;
    protected int ticks;
    public DebugOverlayScene debugOverlay;
    private boolean running;

    protected Game(GameArguments gameArguments, GameInitializer initializer, Scene defaultScene) {
        this.gameArguments = gameArguments;
        this.initializer = initializer;
        this.defaultScene = defaultScene;
        this.ticks = 0;
        this.isPaused = false;
        this.logger = Util.getLogger();
        this.running = true;
    }

    public void initAndRunGame() {
        this.logger.info("Using engine version {}", Engine.getInstance().getVersion());
        this.logger.info("Loading game: {} ({})", this.gameArguments.gameName(), this.gameArguments.defaultNamespace());

        Raylib.SetTraceLogLevel(Raylib.LOG_WARNING);

        // raylib stuffs
        Raylib.SetConfigFlags(Raylib.FLAG_WINDOW_RESIZABLE);
        String windowTitle = String.format("%s %s", this.gameArguments.gameName(), this.gameArguments.versionString());
        Raylib.InitWindow(960, 540, windowTitle);
        Raylib.InitAudioDevice();

        // TODO: define "min target fps" var in GameArguments class
        if (this.gameArguments.targetFps() < 10) throw new IllegalArgumentException("Attempting to set FPS target too low!");
        Raylib.SetTargetFPS(this.gameArguments.targetFps());
        this.camera = new Raylib.Camera2D();

        // engine and game stuffs
        Identifier.setDefaultNamespace(this.gameArguments.defaultNamespace());
        Engine.getInstance().engineInit();
        this.textureManager = TextureManager.getManager();

        this.debugOverlay = new DebugOverlayScene(Engine.getInstance());
        this.debugOverlay.init();
        this.pauseScene = new DefaultPauseScene();
        this.initializer.init(this);

        for (Identifier id : Registry.ROOT.getEntries().keySet()) {
            Registry.ROOT.getEntryFromId(id).getInit().execute();
            Registry.ROOT.getEntryFromId(id).loadTags();
        }

        this.currentScene = this.defaultScene;
        this.currentScene.init();
        Raylib.SetExitKey(EngineKeyBinds.CLOSE_GAME.getCurrentKey());
        Raylib.SetWindowMinSize(960, 540);

        while (!Raylib.WindowShouldClose() && this.running) {
            Raylib.BeginDrawing();
            Raylib.ClearBackground(BLANK);
            Raylib.SetMouseCursor(Raylib.MOUSE_CURSOR_DEFAULT);
            this.update();
            this.render();
            if (!this.isPaused) this.currentScene.update();
            this.currentScene.render();
            if (this.currentScene instanceof PauseableScene pauseableScene && EngineKeyBinds.PAUSE.isKeyPressed()) {
                if (pauseableScene.canPause()) {
                    if (!this.isPaused) {
                        pauseableScene.onPause();
                        this.pauseScene.init();
                    } else {
                        pauseableScene.onUnpause();
                        this.pauseScene.onReplacedOrRemoved();
                    }
                    this.isPaused = !this.isPaused;
                }
            }
            if (this.isPaused) {
                this.pauseScene.update();
                this.pauseScene.render();
            }
            if (EngineKeyBinds.SHOW_FRAMERATE.isKeyDown() && !this.debugOverlay.isActive()) Raylib.DrawFPS(2, 2);
            if (EngineKeyBinds.TOGGLE_DEBUG.isKeyPressed()) {
                this.debugOverlay.active = !this.debugOverlay.active;
            }
            if (this.debugOverlay.active) {
                this.debugOverlay.update();
                this.debugOverlay.render();
            }
            if (EngineKeyBinds.TOGGLE_FULLSCREEN.isKeyPressed()) Raylib.ToggleBorderlessWindowed();
            Raylib.GetFPS();
            Raylib.EndDrawing();
        }
        this.logger.info("Exiting...");
        this.onCloseGame();
        this.currentScene.onReplacedOrRemoved();
        if (this.isPaused) this.pauseScene.onReplacedOrRemoved();
        if (this.currentScene instanceof DoOnGameClose current) current.onGameClose();
        if (this.pauseScene instanceof DoOnGameClose pause) pause.onGameClose();
        Raylib.CloseAudioDevice();
        Raylib.CloseWindow();
    }

    public void render() {}

    /**
     * Global game update function that is always called regardless of the current scene.
     * <p>
     * This is called BEFORE the current scene is updated.
     */
    public void update() {
        this.ticks++;
    }

    public void onCloseGame() {}

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

    public void unpause() {
        if (this.isPaused) {
            if (this.currentScene instanceof PauseableScene pauseableScene) {
                pauseableScene.onUnpause();
            }
            this.pauseScene.onReplacedOrRemoved();
            this.isPaused = false;
        }
    }

    public void closeGame() {
        this.running = false;
    }

    public int getCurrentTick() {
        return this.ticks;
    }

    @FunctionalInterface
    public interface GameInitializer {
        void init(Game game);
    }
}

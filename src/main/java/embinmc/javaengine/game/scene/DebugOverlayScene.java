package embinmc.javaengine.game.scene;

import com.google.gson.internal.JavaVersion;
import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.game.Game;
import embinmc.javaengine.text.Text;
import embinmc.javaengine.util.CoordHelper;

import java.util.ArrayList;
import java.util.List;

public class DebugOverlayScene extends Scene {
    private final Engine engine;
    private final Game game;
    private List<Text> debugLeft;
    private List<Text> debugRight;
    public boolean active;

    public DebugOverlayScene(Engine engine) {
        this.engine = engine;
        this.game = engine.getGame();
        this.active = false;
    }

    @Override
    public void init() {
        this.debugLeft = new ArrayList<>();
        this.debugRight = new ArrayList<>();

        this.debugLeft.add(Text.literal(
                String.format("%s %s (%s/%s)", this.game.getGameArguments().gameName(), this.game.getVersion(), this.game.getNamespace(), this.game.getGameArguments().versionType())
        ).setSize(2));
        this.debugLeft.add(Text.literal("2:fps").setSize(2));
        this.debugLeft.add(Text.literal("3:tick").setSize(2));

        this.debugRight.add(Text.literal(String.format("Java %s", System.getProperty("java.version"))).setSize(2));
        this.debugRight.add(Text.literal(
                String.format("%s %s", this.engine.getGameArguments().gameName(), this.engine.getVersion())
        ).setSize(2));
        this.debugRight.add(Text.literal("2:window").setSize(2));
        this.debugRight.add(Text.literal("3:width").setSize(2));
        this.debugRight.add(Text.literal("4:height").setSize(2));
        //this.debugRight.add(Text.empty());
        //this.debugRight.add(Text.literal(String.format("Working dir: %s", Raylib.GetWorkingDirectory().getString())).setSize(2));
    }

    @Override
    public void update() {
        if (this.active) {
            this.debugLeft.get(1).setText(String.format("%s/%s FPS", Raylib.GetFPS(), this.game.getGameArguments().targetFps()));
            this.debugLeft.get(2).setText(String.format("Current tick: %s", this.game.getCurrentTick()));

            this.debugRight.get(2).setText(String.format("Current monitor: %s", Raylib.GetCurrentMonitor()));
            this.debugRight.get(3).setText(String.format("Window width: %s", Raylib.GetScreenWidth()));
            this.debugRight.get(4).setText(String.format("Window height: %s", Raylib.GetScreenHeight()));
        }
    }

    @Override
    public void render() {
        if (this.active) {
            int index = 0;
            for (Text line : this.debugLeft) {
                int y = (line.getFont().baseSize() + 1) + ((line.getSize() * line.getFont().baseSize()) * index);
                line.setAlignment(Text.Alignment.LEFT).render(2, y);
                index++;
            }
            index = 0;
            for (Text line : this.debugRight) {
                int y = (line.getFont().baseSize() + 1) + ((line.getSize() * line.getFont().baseSize()) * index);
                line.setAlignment(Text.Alignment.RIGHT).render(CoordHelper.fromRight(4), y);
                index++;
            }
        }
    }

    @Override
    public void onReplacedOrRemoved() {

    }

    public boolean isActive() {
        return this.active;
    }
}

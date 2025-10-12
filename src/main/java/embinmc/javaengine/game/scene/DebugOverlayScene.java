package embinmc.javaengine.game.scene;

import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.game.Game;
import embinmc.javaengine.text.Text;

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
                String.format("%s %s", this.engine.getGame().getGameArguments().gameName(), this.engine.getGame().getVersion())
        ).setAlignment(Text.Alignment.LEFT).setSize(2));
        this.debugLeft.add(Text.literal(
                String.format("%s %s", this.engine.getGameArguments().gameName(), this.engine.getVersion())
        ).setAlignment(Text.Alignment.LEFT).setSize(2));
        this.debugLeft.add(Text.literal("2:fps").setAlignment(Text.Alignment.LEFT).setSize(2));
    }

    @Override
    public void update() {
        if (this.active) {
            this.debugLeft.get(2).setText(String.format("%s/%s FPS", Raylib.GetFPS(), this.game.getGameArguments().targetFps()));
        }
    }

    @Override
    public void render() {
        if (this.active) {
            int index = 0;
            for (Text line : this.debugLeft) {
                line.render(2, (line.getFont().baseSize() + 1) + ((line.getSize() * line.getFont().baseSize()) * index));
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

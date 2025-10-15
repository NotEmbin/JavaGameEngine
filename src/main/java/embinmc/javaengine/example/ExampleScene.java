package embinmc.javaengine.example;

import com.raylib.Colors;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.scene.PauseableScene;
import embinmc.javaengine.game.scene.Scene;
import embinmc.javaengine.render.JeTexture;
import embinmc.javaengine.render.Sprite;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.text.Text;

public class ExampleScene extends Scene implements PauseableScene {
    private Text currentTickText;

    @Override
    public void init() {
        this.currentTickText = Text.literal("Tick: 0").setAlignment(Text.Alignment.LEFT).setSize(3).setColor(Colors.GOLD);
        this.backgroundTexture = Sprite.ofTopLeft(Engine.getInstance().getTextureManager().missingnoTexture);
    }

    @Override
    public void update() {
        super.update();
        this.currentTickText.setText(String.format("Tick: %s", this.ticks));
    }

    @Override
    public void render() {
        super.render();
        this.currentTickText.render(256, 256);
    }

    @Override
    public void onReplacedOrRemoved() {
    }
}

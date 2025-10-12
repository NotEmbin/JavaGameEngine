package embinmc.javaengine.example;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.scene.object.GameObject;
import embinmc.javaengine.render.Sprite;

public class ExampleObject extends GameObject {
    public ExampleObject() {
        super(Sprite.ofCenter(Engine.getInstance().getGame().textureManager.missingnoTexture), 96 * 3, 48, 96, 48);
    }

    @Override
    public void render() {
        Raylib.Color tint = isMouseHovering() ? Colors.GOLD : Colors.WHITE;
        this.sprite.render(this.x, this.y, this.width, this.height, tint);
    }

    @Override
    public void update() {

    }
}

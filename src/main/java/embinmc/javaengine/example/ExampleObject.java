package embinmc.javaengine.example;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.scene.object.GameObject;
import embinmc.javaengine.render.JeTexture;
import embinmc.javaengine.render.Sprite;
import embinmc.javaengine.resource.Identifier;

public class ExampleObject extends GameObject {
    private Raylib.Image image;
    private Sprite sprite;

    public ExampleObject() {
        super(Identifier.of("engine", "missingno"), 64, 64, 96, 48);
        //this.texture = Engine.getInstance().getGame().textureManager.getTexture(this.textureId);
        //this.image = Raylib.LoadImageFromTexture(this.texture.texture);
        this.sprite = Sprite.ofCenter(Engine.getInstance().getGame().textureManager.missingnoTexture);
    }

    @Override
    public void render() {
        //Raylib.DrawRectangleRec(this.rect, isMouseHovering() ? Colors.BLUE : Colors.WHITE);
        //Raylib.ImageDrawRectangleRec(this.image, this.rect, isMouseHovering() ? Colors.BLUE : Colors.WHITE);
        //Raylib.ImageDrawRectangle(this.image, this.x, this.y, this.width, this.height, Colors.DARKPURPLE);
        this.sprite.render(96 * 3, 48, 48, 24);
    }

    @Override
    public void update() {

    }
}

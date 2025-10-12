package embinmc.javaengine.game.scene.object;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.Game;
import embinmc.javaengine.render.JeTexture;
import embinmc.javaengine.resource.Identifier;

public abstract class GameObject {
    public Identifier textureId;
    public JeTexture texture;
    public int width, height, x, y;
    public Raylib.Rectangle rect;

    public GameObject(Identifier textureId, int x, int y, int width, int height) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.x = x + (x / 2);
        this.y = y + (y / 2);
        this.rect = new Raylib.Rectangle().width(width).height(height).x(x - (width / 2)).y(y - (height / 2));
        this.texture = Engine.getInstance().getGame().textureManager.missingnoTexture;
        //Raylib.SetShapesTexture(this.texture.texture, this.rect);
    }

    public abstract void render();
    public abstract void update();

    public boolean isMouseHovering() {
        return Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), this.rect);
    }
}

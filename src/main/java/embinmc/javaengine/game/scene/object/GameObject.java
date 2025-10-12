package embinmc.javaengine.game.scene.object;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.game.Game;
import embinmc.javaengine.math.Vec2;
import embinmc.javaengine.render.JeTexture;
import embinmc.javaengine.render.Sprite;
import embinmc.javaengine.resource.Identifier;

public abstract class GameObject {
    public int x, y, width, height;
    public Sprite sprite;

    public GameObject(Sprite sprite, int x, int y, int width, int height) {
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public GameObject(Sprite sprite, Vec2 pos, Vec2 scale) {
        this.sprite = sprite;
        this.width = scale.x();
        this.height = scale.y();
        this.x = pos.x();
        this.y = pos.y();
    }

    public void render() {
        this.sprite.render(this.x, this.y, this.width, this.height);
    }

    public abstract void update();

    public boolean isMouseHovering() {
        if (this.sprite.getRect() == null) return false;
        if (this.sprite.getRect().isNull()) return false;
        return Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(), this.sprite.getRect());
    }
}

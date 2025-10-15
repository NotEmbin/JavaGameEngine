package embinmc.javaengine.game.scene.object;

import com.raylib.Raylib;
import embinmc.javaengine.math.Vec2;
import embinmc.javaengine.render.Sprite;

public abstract class GameObject {
    public int x, y, width, height;
    public Sprite sprite;
    private boolean lastTickHoverStatus;

    public GameObject(Sprite sprite, int x, int y, int width, int height) {
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.lastTickHoverStatus = false;
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

    public boolean onceMouseHovering() {
        boolean lastStatus = this.lastTickHoverStatus;
        this.lastTickHoverStatus = this.isMouseHovering();
        return this.isMouseHovering() != lastStatus;
    }

    public GameObject setX(int x) {
        this.x = x;
        return this;
    }

    public GameObject setY(int y) {
        this.y = y;
        return this;
    }
}

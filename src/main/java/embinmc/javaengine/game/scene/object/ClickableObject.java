package embinmc.javaengine.game.scene.object;

import embinmc.javaengine.Engine;
import embinmc.javaengine.render.Sprite;

public abstract class ClickableObject extends GameObject {
    protected ClickAction clickAction = null;

    public ClickableObject(Sprite sprite, int x, int y, int width, int height) {
        super(sprite, x, y, width, height);
    }

    public ClickableObject setClickAction(ClickAction method) {
        this.clickAction = method;
        return this;
    }

    public void onClick() {
        if (this.clickAction != null) {
            this.clickAction.run(this, Engine.getInstance(), Engine.getInstance().getGame().currentScene);
        }
    }

    public ClickAction getClickAction() {
        return this.clickAction;
    }
}

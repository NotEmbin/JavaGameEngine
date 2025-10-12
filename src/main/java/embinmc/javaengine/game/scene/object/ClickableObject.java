package embinmc.javaengine.game.scene.object;

import embinmc.javaengine.render.Sprite;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.VoidMethod;

public abstract class ClickableObject extends GameObject {
    public VoidMethod clickAction = null;

    public ClickableObject(Sprite sprite, int x, int y, int width, int height) {
        super(sprite, x, y, width, height);
    }

    public ClickableObject setClickAction(VoidMethod method) {
        this.clickAction = method;
        return this;
    }

    public void onClick() {
        if (this.clickAction != null) {
            this.clickAction.execute();
        }
    }
}

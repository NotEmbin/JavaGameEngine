package embinmc.javaengine.game.scene.object;

import com.raylib.Raylib;
import embinmc.javaengine.render.JeTexture;
import embinmc.javaengine.render.Sprite;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.text.Text;

public class ButtonObject extends ClickableObject {
    protected Text text;
    protected final Sprite hoverSprite;
    protected float scale;

    public ButtonObject(Identifier sprite, Identifier hoverSprite, Text text) {
        super(Sprite.ofCenter(JeTexture.of(sprite)), 0, 0, 0, 0);
        this.text = text;
        this.hoverSprite = Sprite.ofCenter(JeTexture.of(hoverSprite));
        this.scale = 6f;
    }

    public ButtonObject(Identifier sprite, Text text) {
        super(Sprite.ofCenter(JeTexture.of(sprite)), 0, 0, 0, 0);
        this.text = text;
        this.hoverSprite = this.sprite;
        this.scale = 6f;
    }

    @Override
    public void update() {
        if (this.isMouseHovering()) {
            Raylib.SetMouseCursor(Raylib.MOUSE_CURSOR_POINTING_HAND);
            if (Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
                this.onClick();
            }
        }
    }

    @Override
    public void render() {
        if (this.isMouseHovering()) {
            this.hoverSprite.renderAutoDim(this.x, this.y, this.scale);
        } else {
            this.sprite.renderAutoDim(this.x, this.y, this.scale);
        }
        this.text.render(this.x, this.y);
    }

    public ButtonObject setText(Text text) {
        this.text = text;
        return this;
    }

    public ButtonObject setScale(float newScale) {
        this.scale = newScale;
        return this;
    }
}

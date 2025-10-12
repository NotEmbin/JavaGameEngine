package embinmc.javaengine.render;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.math.Vec2;

public class Sprite {
    private JeTexture texture;
    private Alignment alignment;
    private Raylib.Rectangle rectSource;
    private Raylib.Rectangle rectDest;
    private final Raylib.Vector2 origin = Vec2.of(0, 0).toRayVec();

    private Sprite(JeTexture texture, Alignment alignment) {
        this.texture = texture;
        this.alignment = alignment;
        this.rectSource = new Raylib.Rectangle()
                .x(0).y(0)
                .width(this.texture.texture.width())
                .height(this.texture.texture.height());
    }

    public static Sprite ofCenter(JeTexture texture) {
        return new Sprite(texture, Alignment.CENTER);
    }

    public static Sprite ofTopLeft(JeTexture texture) {
        return new Sprite(texture, Alignment.TOP_LEFT);
    }

    public void render(int x, int y, int width, int height, Raylib.Color tint) {
        int alignedX = switch (this.alignment) {
            case CENTER -> x - (width / 2);
            case TOP_LEFT -> x;
        };
        int alignedY = switch (this.alignment) {
            case CENTER -> y - (height / 2);
            case TOP_LEFT -> y;
        };
        if (this.rectDest == null || !rectMatches(this.rectDest, alignedX, alignedY, width, height)) {
            this.rectDest = new Raylib.Rectangle().x(alignedX).y(alignedY).width(width).height(height);
        }
        Raylib.DrawTexturePro(this.texture.texture, this.rectSource, this.rectDest, this.origin, 0.0f, tint);
    }

    public void render(int x, int y, int width, int height) {
        this.render(x, y, width, height, Colors.WHITE);
    }

    public static boolean rectMatches(Raylib.Rectangle rect, int x, int y, int width, int height) {
        int rectX = (int) rect.x();
        int rectY = (int) rect.y();
        int rectW = (int) rect.width();
        int rectH = (int) rect.height();

        return !((rectX != x) || (rectY != y) || (rectW != width) || (rectH != height));
    }

    public JeTexture getTexture() {
        return this.texture;
    }

    public Raylib.Rectangle getRect() {
        return this.rectDest;
    }

    protected enum Alignment {
        CENTER,
        TOP_LEFT
    }
}

package embinmc.javaengine.text;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.math.Vec2;
import embinmc.javaengine.registry.EngineRegistries;

public class Text {
    private JeFont font = Fonts.RAYLIB;
    private String text;
    private int size = 3;
    private float spacing = 1f;
    private Raylib.Color color = Colors.WHITE;
    private Alignment alignment = Alignment.LEFT;

    private Text(String text, JeFont font) {
        this.font = font;
        this.text = text;
    }

    private Text(String text) {
        this.text = text;
    }

    public static Text literal(String text) {
        return new Text(text);
    }

    public void render(int x, int y) {
        switch (this.alignment) {
            case LEFT -> Raylib.DrawTextEx(this.font.getFont(), this.text, Vec2.of(x, y).toRayVec(), this.font.baseSize() * this.size, this.spacing * this.size, this.color);
            case CENTER -> {
                float height = this.font.baseSize() * this.size;
                float width = Raylib.MeasureTextEx(this.font.getFont(), this.text, height, this.spacing * this.size).x();
                Raylib.Vector2 pos = new Raylib.Vector2().x(x - (width / 2)).y(y - (height / 2));
                Raylib.DrawTextEx(this.font.getFont(), this.text, pos, height, this.spacing * this.size, this.color);
            }
        }
    }

    public String getText() {
        return text;
    }

    public Text setText(String text) {
        this.text = text;
        return this;
    }

    public int getSize() {
        return this.size;
    }

    public Text setSize(int size) {
        this.size = size;
        return this;
    }

    public float getSpacing() {
        return this.spacing;
    }

    public Text setSpacing(float spacing) {
        this.spacing = spacing;
        return this;
    }

    public Text setColor(Raylib.Color color) {
        this.color = color;
        return this;
    }

    public Alignment getAlignment() {
        return this.alignment;
    }

    public Text setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public enum Alignment {
        LEFT,
        CENTER
    }
}

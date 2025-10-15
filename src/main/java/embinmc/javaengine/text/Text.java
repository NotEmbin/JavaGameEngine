package embinmc.javaengine.text;

import com.raylib.Colors;
import com.raylib.Raylib;

public class Text {
    private static final Text EMPTY = Text.literal("");
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

    public static Text empty() {
        return Text.EMPTY;
    }

    protected void render(int x, int y, boolean withShadow, Raylib.Color color) {
        if (!this.text.isBlank()) {
            if (withShadow) {
                Raylib.Vector3 vec3 = Raylib.ColorToHSV(color);
                Raylib.Color shadowColor = Raylib.ColorFromHSV(vec3.x(), vec3.y(), (float)(vec3.z() * 0.2));
                this.render(x + this.size, y + this.size, false, shadowColor);
            }
            float height = this.getHeight();
            float width = Raylib.MeasureTextEx(this.font.getFont(), this.text, height, this.spacing * this.size).x();
            Raylib.Vector2 pos = new Raylib.Vector2();
            switch (this.alignment) {
                case LEFT -> pos.x(x).y(y - (height / 2));
                case CENTER -> pos.x(x - (width / 2)).y(y - (height / 2));
                case RIGHT -> pos.x(x - width).y(y - (height / 2));
            }
            Raylib.DrawTextEx(this.font.getFont(), this.text, pos, height, this.spacing * this.size, color);
        }
    }

    public void render(int x, int y) {
        this.render(x, y, true, this.color);
    }

    public void renderNoShadow(int x, int y) {
        this.render(x, y, false, this.color);
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

    public float getHeight() {
        return this.font.baseSize() * this.size;
    }

    public JeFont getFont() {
        return this.font;
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
        CENTER,
        RIGHT
    }
}

package embinmc.javaengine.text;

import com.raylib.Raylib;
import org.bytedeco.javacpp.IntPointer;

public class JeFont {
    private final Raylib.Font font;
    private final int baseSize;

    public JeFont(String fontPath, int baseSize) {
        this.font = Raylib.LoadFontEx(fontPath, 64, new IntPointer(), 0);
        this.baseSize = baseSize;
    }

    public JeFont(Raylib.Font font, int baseSize) {
        this.font = font;
        this.baseSize = baseSize;
    }

    public Raylib.Font getFont() {
        return this.font;
    }

    public int baseSize() {
        return this.baseSize;
    }
}

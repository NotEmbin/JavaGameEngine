package embinmc.javaengine.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.raylib.Raylib;
import embinmc.javaengine.data.MoreCodecs;

import java.util.HexFormat;

public record Color(int alpha, int red, int green, int blue) {
    public static final Codec<Color> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MoreCodecs.rangedInt(0, 255).optionalFieldOf("alpha", 255).forGetter(Color::alpha),
            MoreCodecs.rangedInt(0, 255).fieldOf("red").forGetter(Color::red),
            MoreCodecs.rangedInt(0, 255).fieldOf("green").forGetter(Color::green),
            MoreCodecs.rangedInt(0, 255).fieldOf("blue").forGetter(Color::blue)
    ).apply(instance, Color::new));

    public static Codec<Color> getCodec() {
        return Color.CODEC;
    }

    public Raylib.Color toRaylibColor() {
        return Raylib.GetColor(this.toRGBA());
    }

    public String toStringRGBA() {
        return "#" + hexedValue(this.red) + hexedValue(this.green) + hexedValue(this.blue) + hexedValue(this.alpha);
    }

    public String toStringARGB() {
        return "#" + hexedValue(this.alpha) + hexedValue(this.red) + hexedValue(this.green) + hexedValue(this.blue);
    }

    public int toRGBA() {
        int r = this.red << 24;
        int g = this.green << 16;
        int b = this.blue << 8;
        int a = this.alpha;
        return r+g+b+a;
    }

    public int toARGB() {
        int a = this.alpha << 24;
        int r = this.red << 16;
        int g = this.green << 8;
        int b = this.blue;
        return a+r+g+b;
    }

    public static String hexedValue(int v) {
        String hex = Integer.toHexString(v);
        if (hex.length() == 1) return "0" + hex;
        return hex;
    }
}
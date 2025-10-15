package embinmc.javaengine.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.raylib.Raylib;

public record Color(int hex) {
    public static final Codec<Color> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("argb").forGetter(Color::hex)
    ).apply(instance, Color::new));

    public Raylib.Color toRayColor() {
        return Raylib.GetColor(this.hex);
    }
}
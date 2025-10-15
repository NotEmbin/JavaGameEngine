package embinmc.javaengine.math;

import com.mojang.serialization.Codec;
import com.raylib.Raylib;

import java.util.List;

public record Vec2f(float x, float y) {
    private static final Codec<Vec2f> CODEC = Codec.FLOAT.listOf(2, 2).xmap(
            vec2f -> Vec2f.of(vec2f.getFirst(), vec2f.getLast()),
            vec2 -> List.of(vec2.x(), vec2.y())
    );

    public static Vec2f of(float x, float y) {
        return new Vec2f(x, y);
    }

    public static Codec<Vec2f> getCodec() {
        return Vec2f.CODEC;
    }

    public Raylib.Vector2 toRayVec() {
        return new Raylib.Vector2().x(this.x).y(this.y);
    }
}

package embinmc.javaengine.math;

import com.mojang.serialization.Codec;

import java.util.List;

public record Vec2(int x, int y) {
    private static final Codec<Vec2> CODEC = Codec.INT.listOf(2, 2).xmap(
            vec2 -> Vec2.of(vec2.getFirst(), vec2.getLast()),
            vec2 -> List.of(vec2.x(), vec2.y())
    );

    public static Vec2 of(int x, int y) {
        return new Vec2(x, y);
    }

    public static Codec<Vec2> getCodec() {
        return Vec2.CODEC;
    }
}

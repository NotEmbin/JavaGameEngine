package embinmc.javaengine.text;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LiteralTextType(String text) implements TextType {
    public static final MapCodec<LiteralTextType> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING.fieldOf("text").forGetter(LiteralTextType::getContents)
            ).apply(instance, LiteralTextType::new)
    );

    @Override
    public MapCodec<? extends TextType> getCodec() {
        return LiteralTextType.CODEC;
    }

    @Override
    public String getContents(Object... args) {
        return String.format(this.text, args);
    }
}

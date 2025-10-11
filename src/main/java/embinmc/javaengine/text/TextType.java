package embinmc.javaengine.text;

import com.mojang.serialization.MapCodec;

public interface TextType {
    MapCodec<? extends TextType> getCodec();

    String getContents(Object... args);
}

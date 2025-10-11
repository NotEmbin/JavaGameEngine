package embinmc.javaengine.registry.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record TagFile(List<TagEntry> entries, boolean replace) {
    public static Codec<TagFile> getCodec() {
        return RecordCodecBuilder.create(
            instance -> instance.group(
                TagEntry.getCodec().listOf().fieldOf("values").forGetter(TagFile::entries),
                Codec.BOOL.optionalFieldOf("replace", false).forGetter(TagFile::replace)
            ).apply(instance, TagFile::new)
        );
    }
}

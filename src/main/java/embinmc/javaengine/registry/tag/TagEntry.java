package embinmc.javaengine.registry.tag;

import com.mojang.serialization.Codec;
import embinmc.javaengine.resource.Identifier;

/**
 * An entry from the <code>values</code> array of a tag file
 * <p></p>
 * To specify an entry as a tag, prefix the value with a <code>#</code>
 */
public record TagEntry(Identifier id, boolean isTag) {
    private String asString() {
        return this.isTag ? "#" + this.id : this.id.toString();
    }

    @Override
    public String toString() {
        return this.asString();
    }

    public static Codec<TagEntry> getCodec() {
        return Codec.STRING.comapFlatMap(
            entry -> entry.startsWith("#") ?
                Identifier.validate(entry.substring(1)).map(id -> new TagEntry(id, true)) :
                Identifier.validate(entry).map(id -> new TagEntry(id, false)),
            TagEntry::asString
        );
    }
}

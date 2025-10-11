package embinmc.javaengine.registry.tag;

import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;

public final class TagBuilder<T> {
    public final TagFile file;
    public final Identifier identifier;
    public final Registry<T> registry;

    public TagBuilder(Registry<T> registry, Identifier id, TagFile tagFile) {
        this.registry = registry;
        this.identifier = id;
        this.file = tagFile;
    }


}

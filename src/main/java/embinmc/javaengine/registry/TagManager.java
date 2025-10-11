package embinmc.javaengine.registry;

import embinmc.javaengine.registry.tag.TagBuilder;
import embinmc.javaengine.registry.tag.TagEntry;
import embinmc.javaengine.resource.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Manages tags for a registry. Each registry has its own manager
 */
public class TagManager<T> {
    private final Logger logger;
    private final Registry<T> registry;
    private List<TagBuilder<T>> builders = new ArrayList<>(16);
    private List<Tag<T>> finalizedTags;

    public TagManager(Registry<T> registry) {
        this.registry = registry;
        this.logger = LoggerFactory.getLogger("TagManager[" + this.registry.getKey() + "]");
    }

    public TagManager<T> addBuilder(TagBuilder<T> builder) {
        this.builders.add(builder);
        return this;
    }

    public boolean builderExistsInManager(Identifier id) {
        return this.getBuilder(id).isPresent();
    }

    public Optional<TagBuilder<T>> getBuilder(Identifier id) {
        for (TagBuilder<T> builder : builders) {
            if (Objects.equals(id, builder.identifier)) {
                return Optional.of(builder);
            }
        }
        return Optional.empty();
    }

    protected List<RegistryEntry<T>> getEntries(TagBuilder<T> builder) {
        List<RegistryEntry<T>> entries = new ArrayList<>(16);
        for (TagEntry entry : builder.file.entries()) {
            if (entry.isTag()) {
                if (this.builderExistsInManager(entry.id())) {
                    entries.addAll(getEntries(this.getBuilder(entry.id()).get()));
                } else {
                    this.logger.warn("Couldn't add element to #{}: #{} doesn't exist!", builder.identifier, entry.id());
                }
            } else {
                if (this.registry.hasEntry(entry.id())) {
                    entries.add(RegistryEntry.get(this.registry, this.registry.getEntryFromId(entry.id()), entry.id()));
                } else {
                    this.logger.warn("Couldn't add element to #{}: {} doesn't contain {}!", builder.identifier, this.registry.getKey(), entry.id());
                }
            }
        }
        return entries;
    }

    public List<Tag<T>> buildTags() {
        this.finalizedTags.clear();
        List<Tag<T>> tags = new ArrayList<>(16);
        for (TagBuilder<T> builder : builders) {
            List<RegistryEntry<T>> entries = this.getEntries(builder);
            tags.add(new Tag<>(this.registry, builder.identifier, entries));
        }
        this.finalizedTags.addAll(tags);
        return this.finalizedTags;
    }

    public static class Tag<T> {
        private Registry<T> registry;
        private List<RegistryEntry<T>> entries;
        private Identifier id;

        private Tag(Registry<T> registry, Identifier id, List<RegistryEntry<T>> entries) {
            this.registry = registry;
            this.id = id;
            this.entries = entries;
        }

        public boolean has(RegistryEntry<T> entry) {
            return entries.contains(entry);
        }
    }
}

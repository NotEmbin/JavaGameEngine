package embinmc.javaengine.registry;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import embinmc.javaengine.registry.tag.TagBuilder;
import embinmc.javaengine.registry.tag.TagFile;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.resource.util.FileManaging;
import embinmc.javaengine.resource.util.GsonUtil;
import embinmc.javaengine.util.Util;
import embinmc.javaengine.util.VoidMethod;
import embinmc.javaengine.util.exception.DuplicateRegistryEntryException;
import embinmc.javaengine.util.exception.UnknownRegistryEntryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Registry<T> {
    public static final Logger LOGGER = LoggerFactory.getLogger("Registry");
    public static final Registry<Registry<?>> ROOT = new Registry<>(Identifier.ofEngine("root"), EngineRegistries::init);
    private final Identifier key;
    private final Map<Identifier, T> entries;
    private final Map<T, Identifier> reversed_entries;
    private final TagManager<T> tagManager;
    protected VoidMethod init;
    private T default_entry;

    protected Registry(Identifier key, VoidMethod init) {
        this.key = key;
        this.entries = new HashMap<>(0);
        this.reversed_entries = new HashMap<>(0);
        this.tagManager = new TagManager<>(this);
        this.init = init;
    }

    public static <T> Registry<T> create(Identifier key, VoidMethod init) {
        LOGGER.info("Creating registry {}", key);
        return Registry.register(Registry.ROOT, new Registry<>(key, init), key, false);
    }

    public static <T> Registry<T> create(String key, VoidMethod init) {
        return create(Identifier.from(key), init);
    }

    /**
     * Used by the engine to create its own registries.
     * Should NOT be used by a game.
     */
    public static <T> Registry<T> createEngine(String key, VoidMethod init) {
        return create(Identifier.ofEngine(key), init);
    }

    public static <T> DataLoadableRegistry<T> createDataLoadable(Identifier key, Codec<T> codec) {
        LOGGER.info("Creating data loadable registry {}", key);
        return Registry.register(Registry.ROOT, new DataLoadableRegistry<>(key, codec), key, false);
    }

    public static <T> DataLoadableRegistry<T> createDataLoadable(String key, Codec<T> codec) {
        return createDataLoadable(Identifier.from(key), codec);
    }

    /**
     * Used by the engine to create its own data driven registries.
     * Should NOT be used by a game.
     */
    public static <T> DataLoadableRegistry<T> createEngineDataLoadable(String key, Codec<T> codec) {
        return createDataLoadable(Identifier.ofEngine(key), codec);
    }

    protected void addEntry(T entry, Identifier id, boolean log_entry_creation) {
        if (!this.hasEntry(id)) {
            this.entries.put(id, entry);
            this.reversed_entries.put(entry, id);
            if (log_entry_creation) LOGGER.info("Loaded registry entry [{} / {}]", this.getKey(), id);
        } else {
            throw new DuplicateRegistryEntryException("Cannot add [" + this.getKey() + " / " + id + "], as it already exists!");
        }
    }

    public Map<Identifier, T> getEntries() {
        return this.entries;
    }

    public Identifier getIdFromEntry(T entry) {
        return this.reversed_entries.get(entry);
    }

    public T getEntryFromId(Identifier id) {
        for (Identifier entry : this.get_all_ids()) {
            if (id.equals(entry)) {
                return this.entries.get(entry);
            }
        }
        throw new UnknownRegistryEntryException("Registry entry [" + this.getKey() + " / " + id + "] does not exist!");
    }

    public Optional<RegistryEntry<T>> getEntryOptional(Identifier id) {
        if (this.hasEntry(id)) {
            return Optional.of(RegistryEntry.get(this, getEntryFromId(id), id));
        } else {
            return Optional.empty();
        }
    }

    public T getEntryOrDefault(Identifier id) {
        try {
            return getEntryFromId(id);
        } catch (UnknownRegistryEntryException ex) {
            return this.getDefaultEntry();
        }
    }

    public Identifier getKey() {
        return this.key;
    }

    public String get_translation_prefix() {
        return this.key.getPath();
    }

    public Set<Identifier> get_all_ids() {
        return this.entries.keySet();
    }

    public boolean hasEntry(Identifier id) {
        for (Identifier entry : this.entries.keySet()) {
            if (id.equals(entry)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEntry(T entry) {
        return this.entries.containsValue(entry);
    }

    public int get_registry_size() {
        return this.entries.size();
    }

    public static <T> T register(Registry<? super T> registry, T entry, Identifier id, boolean log_entry_creation) {
        registry.addEntry(entry, id, log_entry_creation);
        return entry;
    }

    public static <T> T register(Registry<? super T> registry, T entry, Identifier id) {
        return register(registry, entry, id, true);
    }

    public static <V, T extends V> T register(Registry<V> registry, T entry, String id) {
        return (T) register(registry, entry, Identifier.from(id));
    }

    public static <T> T getFromRegistryKey(RegistryKey<T> key) {
        return key.getRegistry().getEntryFromId(key.getId());
    }

    public T get_from_key(RegistryKey<T> key) {
        if (key.getRegistry() != this) throw new RuntimeException("Expected registry \"" + this.getKey() + "\" in " + key);
        return this.getEntryFromId(key.getId());
    }

    public void set_default_entry(T entry) {
        this.default_entry = entry;
    }

    public T getDefaultEntry() {
        return this.default_entry;
    }

    public Codec<RegistryEntry<T>> getEntryCodec() {
        return Identifier.getCodec().comapFlatMap(
                id -> this.getEntryOptional(id).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Entry " + id + " does not exist in " + this.key)),
                RegistryEntry::getId
        );
    }

    public VoidMethod getInit() {
        return this.init;
    }

    @Override
    public String toString() {
        return "Registry[" + this.getKey() + "]";
    }

    public void loadTags() {
        String checkFolder = "tags/" + this.getKey().toShortString().replaceFirst(":", "/");
        List<String> namespacesToSearch = FileManaging.getNamespacesWithFolder(checkFolder);
        Map<Identifier, JsonObject> mappedResults = HashMap.newHashMap(64);
        for (String namespace : namespacesToSearch) {
            String base = "data/" + namespace + "/" + checkFolder + "/";
            for (String path : FileManaging.getPathsInFolder(base)) {
                for (String fileExtension : GsonUtil.JSON_EXTENSIONS) {
                    if (path.endsWith(fileExtension)) {
                        String newPath = FileManaging.removeBaseFromFile(base, path);
                        Identifier id = Identifier.of(namespace, Util.removeEndFromString(newPath, fileExtension));
                        JsonObject json = FileManaging.getJsonFile(path);
                        mappedResults.put(id, json);
                        break;
                    }
                }
                //if (!mappedResults.containsKey(id)) this.registryLogger.warn("Couldn't load file {} to {}", path, this.getKey());
            }
        }
        for (Map.Entry<Identifier, JsonObject> entry : mappedResults.entrySet()) {
            DataResult<TagFile> result = TagFile.getCodec().parse(JsonOps.INSTANCE, entry.getValue());
            if (result.isSuccess()) {
                this.tagManager.addBuilder(new TagBuilder<>(this, entry.getKey(), result.getOrThrow()));
                LOGGER.info("Created {} tag \"{}\"", this.getKey(), entry.getKey());
            }
            if (result.isError()) LOGGER.error("Failed to load tag {} in {}: {}", entry.getKey(), this.getKey(), result.error().get());
        }
    }
}

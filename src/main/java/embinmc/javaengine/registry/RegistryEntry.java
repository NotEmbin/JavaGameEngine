package embinmc.javaengine.registry;

import embinmc.javaengine.resource.Identifier;

public class RegistryEntry<T> {
    private final Registry<T> registry;
    private final Identifier id;
    private final T value;

    private RegistryEntry(Registry<T> registry, T value, Identifier id) {
        this.registry = registry;
        this.id = id;
        this.value = value;
    }

    public static <T> RegistryEntry<T> get(Registry<T> registry, T value, Identifier id) {
        return new RegistryEntry<>(registry, value, id);
    }

    public static <T> RegistryEntry<T> get(RegistryKey<T> key) {
        return new RegistryEntry<>(key.getRegistry(), Registry.getFromRegistryKey(key), key.getId());
    }

    public T getValue() {
        return this.value;
    }

    public Identifier getId() {
        return this.id;
    }

    public Registry<T> getRegistry() {
        return this.registry;
    }
}

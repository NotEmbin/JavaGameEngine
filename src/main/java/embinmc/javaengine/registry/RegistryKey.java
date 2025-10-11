package embinmc.javaengine.registry;

import embinmc.javaengine.resource.Identifier;

public class RegistryKey<T> {
    private final Registry<T> registry;
    private final Identifier id;
    private RegistryKey(Registry<T> registry, Identifier id) {
        this.registry = registry;
        this.id = id;
    }

    @Override
    public String toString() {
        return "RegistryKey[" + this.registry.getKey() + " / " + this.id + "]";
    }

    public static <T> RegistryKey<T> of(Registry<T> registry, Identifier id) {
        return new RegistryKey<>(registry, id);
    }

    public Registry<T> getRegistry() {
        return this.registry;
    }

    public Identifier getId() {
        return this.id;
    }

    public String toFileDataPath(String fileExtension) {
        StringBuilder builder = new StringBuilder("data/");
        builder.append(this.id.getNamespace()).append("/");
        if (!this.getRegistry().getKey().isNamespaceVanilla()) builder.append(this.getRegistry().getKey().getNamespace()).append("/");
        builder.append(this.getRegistry().getKey().getPath()).append("/");
        builder.append(this.getId().getPath()).append(fileExtension);
        return builder.toString();
    }

    public String toFileDataPath() {
        return this.toFileDataPath(".json");
    }
}

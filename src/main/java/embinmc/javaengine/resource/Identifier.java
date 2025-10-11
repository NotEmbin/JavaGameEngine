package embinmc.javaengine.resource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import embinmc.javaengine.Engine;
import embinmc.javaengine.util.exception.NamespaceIdentifierException;

import java.util.Arrays;
import java.util.Objects;

public class Identifier implements Comparable<Identifier> {
    private static final Codec<Identifier> CODEC = Codec.STRING.comapFlatMap(Identifier::validate, Identifier::toString).stable();
    public static boolean SHOULD_VALIDATE_ID = true;
    private final String namespace;
    private final String path;
    public static String DEFAULT_NAMESPACE = "fnafworld";
    public static String SEPARATOR = ":";

    private Identifier(String namespace, String path) {
        this.namespace = validateNamespace(namespace);
        this.path = validatePath(path);
    }

    @Override
    public String toString() {
        return this.to_string();
    }

    @Deprecated
    public String to_string() {
        return this.namespace + SEPARATOR + this.path;
    }

    public static Identifier of(String namespace, String path) {
        return new Identifier(namespace, path);
    }

    public static Identifier ofDefault(String path) {
        return new Identifier(DEFAULT_NAMESPACE, path);
    }

    public static Identifier ofEngine(String path) {
        return new Identifier(Engine.getInstance().getNamespace(), path);
    }

    public static Identifier from(String id) {
        String[] splitted = id.split(SEPARATOR);
        if (splitted.length == 1) {
            return new Identifier(DEFAULT_NAMESPACE, splitted[0]);
        }
        if (splitted.length == 2) {
            return new Identifier(splitted[0], splitted[1]);
        }
        throw new NamespaceIdentifierException("Invalid namespace: " + id);
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String toShortString() {
        if (this.isNamespaceVanilla()) {
            return this.path;
        }
        return this.toString();
    }

    public boolean isNamespaceVanilla() {
        return Objects.equals(this.namespace, Identifier.DEFAULT_NAMESPACE);
    }

    public String toTranslationKey() {
        return this.namespace + '.' + this.path.replace('/', '.');
    }

    public String toTranslationKey(String prefix) {
        return prefix + '.' + this.toTranslationKey();
    }

    public static boolean isNamespaceValid(String namespace) {
        if (SHOULD_VALIDATE_ID) {
            return namespace.matches(getNamespaceRegex());
        }
        return true;
    }

    public static boolean isPathValid(String path) {
        if (SHOULD_VALIDATE_ID) {
            return path.matches(getPathRegex());
        }
        return true;
    }

    public static DataResult<String> isNamespaceValidDataResult(String namespace) {
        if (Identifier.isNamespaceValid(namespace)) return DataResult.success(namespace);
        return DataResult.error(() -> namespace + " is not a valid namespace!");
    }

    public static DataResult<String> isPathValidDataResult(String path) {
        if (Identifier.isPathValid(path)) return DataResult.success(path);
        return DataResult.error(() -> path + " is not a valid path!");
    }

    public static String validateNamespace(String namespace) {
        if (isNamespaceValid(namespace)) {
            return namespace;
        } else {
            throw new NamespaceIdentifierException("Invalid namespace: " + namespace);
        }
    }

    public static String validatePath(String path) {
        if (isPathValid(path)) {
            return path;
        } else {
            throw new NamespaceIdentifierException("Invalid path: " + path);
        }
    }

    public static String getNamespaceFromFilePath(String path) {
        String formattedPath1 = path;
        if (path.startsWith("data\\")) formattedPath1 = path.replaceFirst("data\\\\", "");
        if (path.startsWith("assets\\")) formattedPath1 = path.replaceFirst("assets\\\\", "");
        return Arrays.stream(formattedPath1.split("\\\\")).toList().getFirst();
    }

    @Override
    public int compareTo(Identifier id) {
        int i = this.path.compareTo(id.path);
        if (i == 0) {
            i = this.namespace.compareTo(id.namespace);
        }
        return i;
    }

    public Identifier with_prefix(String prefix) {
        return new Identifier(this.getNamespace(), prefix + "/" + this.getPath());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Identifier id)) {
            return false;
        } else {
            return this.namespace.equals(id.namespace) && this.path.equals(id.path);
        }
    }

    public static String setDefaultNamespace(String namespace) {
        Identifier.DEFAULT_NAMESPACE = namespace;
        return namespace;
    }

    public static String getNamespaceRegex() {
        return "^[a-z0-9_.-]+$";
    }

    public static String getPathRegex() {
        return "^[a-z0-9_./-]+$";
    }

    public static DataResult<Identifier> validate(String id) {
        try {
            return DataResult.success(from(id));
        } catch (Exception e) {
            return DataResult.error(() -> id + " is not a valid identifier");
        }
    }

    public static Codec<Identifier> getCodec() {
        return Identifier.CODEC;
    }

    // Deprecated below

    @Deprecated
    public String to_texture_path() {
        return "assets/" + this.getNamespace() + "/textures/" + this.getPath() + ".png";
    }

    @Deprecated
    public String to_resource_path(String resource, String file_ext) {
        return "assets/" + this.getNamespace() + "/" + resource + "/" + this.getPath() + "." + file_ext;
    }

    @Deprecated
    public String to_resource_path() {
        return "assets/" + this.getNamespace() + "/" + this.getPath();
    }

    @Deprecated
    public String as_assets_path() {
        return "assets/" + this.getNamespace() + "/" + this.getPath();
    }

    @Deprecated
    public String as_data_path() {
        return "data/" + this.getNamespace() + "/" + this.getPath();
    }

    // Deprecated FOR REMOVAL below

    @Deprecated(forRemoval = true)
    public static Identifier from_texture_path(String path) {
        String newpath = path.replaceAll("\\\\", "/");
        newpath = newpath.replaceFirst("src/main/resources/", "");
        newpath = newpath.replaceFirst("assets/", "");
        newpath = newpath.replaceFirst("/", ":");
        newpath = newpath.replaceFirst("textures/", "");
        newpath = newpath.substring(0, newpath.length() - 4);
        return Identifier.from(newpath);
    }

    @Deprecated(forRemoval = true)
    public Identifier from_resource_path(String resource, String file_ext) {
        return null;
    }
}

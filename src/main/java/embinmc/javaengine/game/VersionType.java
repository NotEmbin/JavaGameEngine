package embinmc.javaengine.game;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public enum VersionType {
    RELEASE("release"),
    SNAPSHOT("snapshot"),
    EXPERIMENTAL("experimental"),
    PREVIEW("preview"),
    BETA("beta"),
    ALPHA("alpha"),
    DEV("dev");

    public final String name;

    private static final Codec<VersionType> CODEC = Codec.STRING.comapFlatMap(VersionType::validate, VersionType::name);

    VersionType(String name) {
        this.name = name;
    }

    public static DataResult<VersionType> validate(String value) {
        try {
            return DataResult.success(VersionType.valueOf(value));
        } catch (IllegalArgumentException _) {
            return DataResult.error(() -> String.format("\"%s\" is not a valid version type!", value));
        }
    }

    public static Codec<VersionType> getCodec() {
        return VersionType.CODEC;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

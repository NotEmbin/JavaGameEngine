package embinmc.javaengine.game;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.javaengine.data.MoreCodecs;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.resource.util.FileManaging;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

public record GameArguments(String gameName, String defaultNamespace, String versionString, int dataVersion, int targetFps) {
    private static final Logger LOGGER = Util.getLogger();
    private static final Codec<GameArguments> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.string(1, 64).fieldOf("game_name").forGetter(GameArguments::gameName),
                    Codec.STRING.comapFlatMap(Identifier::isNamespaceValidDataResult, String::toString).fieldOf("default_namespace").forGetter(GameArguments::defaultNamespace),
                    Codec.string(1, 64).fieldOf("version").forGetter(GameArguments::versionString),
                    MoreCodecs.POSITIVE_INT.fieldOf("data_version").forGetter(GameArguments::dataVersion),
                    MoreCodecs.rangedInt(10, 240).optionalFieldOf("target_fps", 60).forGetter(GameArguments::targetFps)
            ).apply(instance, GameArguments::new)
    );

    public static Codec<GameArguments> getCodec() {
        return GameArguments.CODEC;
    }

    public static GameArguments createInstance(String filePath) {
        try {
            return GameArguments.getCodec().parse(JsonOps.INSTANCE, FileManaging.getJsonFile(filePath)).getOrThrow();
        } catch (Exception e) {
            GameArguments.LOGGER.error("An exception occurred whilst attempting to load '{}'", filePath);
            throw new IllegalStateException(e);
        }
    }
}

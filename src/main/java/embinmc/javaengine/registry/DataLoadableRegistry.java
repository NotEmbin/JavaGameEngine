package embinmc.javaengine.registry;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.resource.util.FileManaging;
import embinmc.javaengine.resource.util.GsonUtil;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoadableRegistry<T> extends Registry<T> {
    private final Codec<T> codecForEntries;
    private final Logger registryLogger;

    protected DataLoadableRegistry(Identifier key, Codec<T> codec) {
        super(key, null);
        this.codecForEntries = codec;
        this.registryLogger = LoggerFactory.getLogger("Registry[" + this.getKey() + "]");
        this.init = this::loadData;
    }

    public void loadData() {
        String checkFolder = this.getKey().toShortString().replaceFirst(":", "/");
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
            DataResult<T> result = this.codecForEntries.parse(JsonOps.INSTANCE, entry.getValue());
            if (result.isSuccess()) {
                this.addEntry(result.getOrThrow(), entry.getKey(), true);
            }
            if (result.isError()) this.registryLogger.error("Failed to load {} in {}: {}", entry.getKey(), this.getKey(), result.error().get());
        }
    }
}

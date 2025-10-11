package embinmc.javaengine.resource.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
    private static final Gson GSON = new Gson();
    public static List<String> JSON_EXTENSIONS = List.of(".json");

    public static JsonObject fromInputStream(InputStream inputStream) {
        return GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);
    }

    public static List<JsonObject> fromFolder(String path) {
        List<String> folderContents = FileManaging.getPathsInFolder(path);
        List<JsonObject> jsons = new ArrayList<>(folderContents.size() * 2);
        for (String filePath : folderContents) {
            for (String extension : GsonUtil.JSON_EXTENSIONS) {
                if (filePath.endsWith(extension)) {
                    jsons.add(FileManaging.getJsonFile(filePath));
                    break;
                }
            }
        }
        return jsons;
    }
}

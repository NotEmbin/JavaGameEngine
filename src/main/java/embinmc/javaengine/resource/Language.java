package embinmc.javaengine.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import embinmc.javaengine.resource.util.FileManaging;

public class Language {
    private final JsonObject langData;

    public Language(String langFile) {
        this.langData = FileManaging.getJsonFile(langFile);
    }

    public String getTranslationOfKey(String key) {
        JsonElement element = this.langData.get(key);
        if (element != null) return element.getAsString();
        return key;
    }

    public boolean translationKeyExists(String key) {
        return this.langData.has(key);
    }
}

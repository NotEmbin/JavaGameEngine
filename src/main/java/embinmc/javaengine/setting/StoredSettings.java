package embinmc.javaengine.setting;

import embinmc.javaengine.resource.Identifier;

import java.util.Map;

public class StoredSettings {
    public int createdDataVersion;
    public int dataVersion;
    public Map<Identifier, Integer> settings;
    public Map<Identifier, String> controls;
}

package embinmc.javaengine.example;

import com.raylib.Raylib;
import embinmc.javaengine.control.KeyBind;
import embinmc.javaengine.registry.EngineRegistries;
import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;

public interface ExampleKeyBinds {
    KeyBind EXAMPLE_KEY = register("example_key", Raylib.KEY_KP_0);

    private static KeyBind register(String id, int defaultKey) {
        return Registry.register(EngineRegistries.KEY_BIND, new KeyBind(defaultKey), Identifier.from(id));
    }

    static void init() {
        Util.getLogger().info("Loading game key binds...");
    }
}

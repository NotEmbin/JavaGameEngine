package embinmc.javaengine.registry;

import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.control.KeyBind;
import org.slf4j.LoggerFactory;

public interface EngineRegistries {
    Registry<KeyBind> KEY_BIND = Registry.createEngine("key_binds", EngineKeyBinds::init);

    static void init() {
        LoggerFactory.getLogger("EngineRegistries").info("Created engine registries...");
    }
}

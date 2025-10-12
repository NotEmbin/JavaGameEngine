package embinmc.javaengine.registry;

import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.control.KeyBind;
import embinmc.javaengine.text.Fonts;
import embinmc.javaengine.text.JeFont;
import org.slf4j.LoggerFactory;

public interface EngineRegistries {
    Registry<KeyBind> KEY_BIND = Registry.createEngine("key_binds", EngineKeyBinds::init);
    Registry<JeFont> FONT = Registry.createEngine("fonts", Fonts::init);

    static void init() {
        LoggerFactory.getLogger("EngineRegistries").info("Created engine registries...");
    }
}

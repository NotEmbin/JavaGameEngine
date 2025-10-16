package embinmc.javaengine.registry;

import embinmc.javaengine.control.EngineKeyBinds;
import embinmc.javaengine.control.KeyBind;
import embinmc.javaengine.text.Fonts;
import embinmc.javaengine.text.JeFont;
import embinmc.javaengine.util.Color;
import embinmc.javaengine.util.Util;
import org.slf4j.LoggerFactory;

public interface EngineRegistries {
    Registry<KeyBind> KEY_BIND = Registry.createEngine("key_binds", EngineKeyBinds::init);
    Registry<JeFont> FONT = Registry.createEngine("fonts", Fonts::init);
    DataLoadableRegistry<Color> COLOR = Registry.createEngineDataLoadable("colors", Color.getCodec());

    static void init() {
        Util.getLogger().info("Created engine registries...");
    }
}

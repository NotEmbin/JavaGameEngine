package embinmc.javaengine.text;

import com.raylib.Raylib;
import embinmc.javaengine.control.KeyBind;
import embinmc.javaengine.registry.EngineRegistries;
import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;

public interface Fonts {
    JeFont DEFAULT = register("engine:default", 12);
    JeFont RAYLIB = Registry.register(EngineRegistries.FONT, new JeFont(Raylib.GetFontDefault(), 10), Identifier.of("raylib", "default"));

    private static JeFont register(String id, int baseSize) {
        Identifier fontId = Identifier.from(id);
        String fontPath = String.format("assets/%s/font/%s.ttf", fontId.getNamespace(), fontId.getPath());
        return Registry.register(EngineRegistries.FONT, new JeFont(fontPath, baseSize), fontId);
    }

    static void init() {
        Util.getLogger().info("Loading engine fonts...");
    }
}

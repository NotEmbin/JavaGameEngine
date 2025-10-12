package embinmc.javaengine.control;

import com.raylib.Raylib;
import embinmc.javaengine.registry.EngineRegistries;
import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;

public interface EngineKeyBinds {
    KeyBind TOGGLE_FULLSCREEN = register("toggle_fullscreen", Raylib.KEY_F11);
    KeyBind CLOSE_GAME = register("close_game", Raylib.KEY_BACKSPACE);
    KeyBind PAUSE = register("pause", Raylib.KEY_ESCAPE);
    KeyBind SHOW_FRAMERATE = register("show_framerate", Raylib.KEY_F2);
    KeyBind TOGGLE_DEBUG = register("toggle_debug_screen", Raylib.KEY_F3);
    KeyBind TEST = register("test_key", Raylib.KEY_B);

    private static KeyBind register(String id, int defaultKey) {
        return Registry.register(EngineRegistries.KEY_BIND, new KeyBind(defaultKey), Identifier.ofEngine(id));
    }

    static void init() {
        Util.getLogger().info("Loading engine key binds...");
    }
}

package embinmc.javaengine.example;

import com.raylib.Raylib;
import embinmc.javaengine.control.KeyBind;
import embinmc.javaengine.registry.EngineRegistries;
import embinmc.javaengine.registry.Registry;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;

public interface ExampleKeyBinds {
    KeyBind MOVE_CAM_LEFT = register("move_cam_left", Raylib.KEY_LEFT);
    KeyBind MOVE_CAM_RIGHT = register("move_cam_right", Raylib.KEY_RIGHT);
    KeyBind MOVE_CAM_UP = register("move_cam_up", Raylib.KEY_UP);
    KeyBind MOVE_CAM_DOWN = register("move_cam_down", Raylib.KEY_DOWN);

    private static KeyBind register(String id, int defaultKey) {
        return Registry.register(EngineRegistries.KEY_BIND, new KeyBind(defaultKey), Identifier.from(id));
    }

    static void init() {
        Util.getLogger().info("Loading game key binds...");
    }
}

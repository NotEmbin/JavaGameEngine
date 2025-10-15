package embinmc.javaengine.game.scene.object;

import embinmc.javaengine.Engine;
import embinmc.javaengine.game.scene.Scene;

@FunctionalInterface
public interface ClickAction {
    void run(ClickableObject button, Engine engine, Scene currentScene);
}

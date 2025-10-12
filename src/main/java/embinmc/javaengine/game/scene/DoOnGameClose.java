package embinmc.javaengine.game.scene;

/**
 * Allows a scene to do something specifically when closing the game.
 * <p>
 * Only works if this scene is the current scene or pause scene.
 * <p>
 * Keep in mind that the {@link Scene#onReplacedOrRemoved()} method will still be called and occur before the {@link DoOnGameClose#onGameClose()} method is called.
 */
public interface DoOnGameClose {
    void onGameClose();
}

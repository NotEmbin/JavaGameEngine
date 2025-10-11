package embinmc.javaengine.game.scene;

/**
 * By default, {@link Scene scenes} do not let the player pause the game.
 * <p>
 * If a scene implements {@link PauseableScene this interface}, the player will be able to pause.
 *
 * @see Scene
 */
public interface PauseableScene {
    default void onPause() {}
    default void onUnpause() {}
}

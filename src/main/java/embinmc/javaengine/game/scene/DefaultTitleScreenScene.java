package embinmc.javaengine.game.scene;

import com.raylib.Raylib;

public class DefaultTitleScreenScene extends PersistantMusicScene {
    public DefaultTitleScreenScene(Raylib.Music music) {
        super(music);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onReplacedOrRemoved() {

    }
}

package embinmc.javaengine.game.scene;

import com.raylib.Raylib;

import java.util.function.Function;

/**
 * An extension of the {@link Scene} class that allows music to persist between other scenes that also extend the {@link PersistantMusicScene} class.
 */
public abstract class PersistantMusicScene extends Scene {
    protected Raylib.Music persistantMusic;
    protected boolean stopPersisting = false;

    public PersistantMusicScene(Raylib.Music music) {
        this.persistantMusic = music;
    }

    @Override
    public void init() {
        if (this.persistantMusic != null) {
            if (!Raylib.IsMusicStreamPlaying(this.persistantMusic)) {
                Raylib.PlayMusicStream(this.persistantMusic);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        Raylib.UpdateMusicStream(this.persistantMusic);
    }

    @Override
    public void onReplacedOrRemoved() {
        if (this.stopPersisting) {
            Raylib.StopMusicStream(this.persistantMusic);
            Raylib.UnloadMusicStream(this.persistantMusic);
        }
    }

    public Raylib.Music getMusic() {
        return this.persistantMusic;
    }

    public boolean shouldStopPersisting() {
        return this.stopPersisting || this.persistantMusic.isNull();
    }
}

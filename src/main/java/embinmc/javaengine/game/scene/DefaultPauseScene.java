package embinmc.javaengine.game.scene;

import com.raylib.Colors;
import com.raylib.Raylib;

public class DefaultPauseScene extends Scene {
    private Raylib.Music music;
    private float volume;

    @Override
    public void init() {
        this.ticks = 0;
        this.music = Raylib.LoadMusicStream("assets/examplegame/music/pause1.wav");
        Raylib.SetMusicVolume(this.music, 0.0f);
        Raylib.PlayMusicStream(this.music);
    }

    @Override
    public void update() {
        super.update();
        this.volume = Math.clamp(((float) this.ticks - 60) / 1000, 0.0f, 0.5f);
        Raylib.SetMusicVolume(this.music, this.volume);
        Raylib.UpdateMusicStream(this.music);
    }

    @Override
    public void render() {
        super.render();
        Raylib.DrawRectangle(0, 0, Raylib.GetScreenWidth(), Raylib.GetScreenHeight(), Raylib.GetColor(0x66000066));
        Raylib.DrawText(String.format("Volume: %s", this.volume), 24, 32, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Pause Tick: %s", this.ticks), 24, 256, 24, Colors.MAGENTA);
        int posX = (Raylib.GetScreenWidth() / 2) - (Raylib.MeasureText("Paused", 48) / 2);
        int posY = (Raylib.GetScreenHeight() / 2) - 24;
        Raylib.DrawText("Paused", posX, posY, 48, Colors.WHITE);
    }

    @Override
    public void onReplacedOrRemoved() {

        Raylib.StopMusicStream(this.music);
        Raylib.UnloadMusicStream(this.music);
    }
}

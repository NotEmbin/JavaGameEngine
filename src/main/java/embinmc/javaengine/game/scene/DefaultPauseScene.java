package embinmc.javaengine.game.scene;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.example.ExampleObject;

public class DefaultPauseScene extends Scene implements DoOnGameClose {
    private Raylib.Music music, music2, music3, music4;
    private float volume, volume2, volume3, volume4;
    private Raylib.Rectangle bg;

    public DefaultPauseScene() {
        this.addObject(new ExampleObject());
    }

    @Override
    public void init() {
        this.ticks = 0;
        if (this.music == null) this.music = Raylib.LoadMusicStream("assets/examplegame/music/pause1.wav");
        if (this.music2 == null) this.music2 = Raylib.LoadMusicStream("assets/examplegame/music/pause2.wav");
        if (this.music3 == null) this.music3 = Raylib.LoadMusicStream("assets/examplegame/music/pause3.wav");
        if (this.music4 == null) this.music4 = Raylib.LoadMusicStream("assets/examplegame/music/pause4.wav");
        Raylib.SetMusicVolume(this.music, 0.0f);
        Raylib.SetMusicVolume(this.music2, 0.0f);
        Raylib.SetMusicVolume(this.music3, 0.0f);
        Raylib.SetMusicVolume(this.music4, 0.001f);
        Raylib.PlayMusicStream(this.music);
        Raylib.PlayMusicStream(this.music2);
        Raylib.PlayMusicStream(this.music3);
        Raylib.PlayMusicStream(this.music4);
        this.bg = new Raylib.Rectangle().x(0).y(0).width(Raylib.GetScreenWidth()).height(Raylib.GetScreenHeight());
    }

    @Override
    public void update() {
        super.update();
        this.volume = Math.clamp(((float) this.ticks - 240) / 2000, 0.0f, 0.25f);
        this.volume2 = Math.clamp(((float) this.ticks - 2400) / 2000, 0.0f, 0.2f);
        this.volume3 = Math.clamp(((float) this.ticks - 4800) / 2000, 0.001f, 0.2f);
        this.volume4 = Math.clamp(((float) this.ticks - 9000) / 2000, 0.001f, 0.2f);
        Raylib.SetMusicVolume(this.music, this.volume);
        Raylib.SetMusicVolume(this.music2, this.volume2);
        Raylib.SetMusicVolume(this.music3, this.volume3);
        Raylib.SetMusicVolume(this.music4, this.volume4);
        Raylib.UpdateMusicStream(this.music);
        Raylib.UpdateMusicStream(this.music2);
        Raylib.UpdateMusicStream(this.music3);
        Raylib.UpdateMusicStream(this.music4);
    }

    @Override
    public void render() {
        Raylib.DrawRectangleRec(this.bg, Raylib.GetColor(0x66000066));
        //Raylib.DrawRectangle(0, 0, Raylib.GetScreenWidth(), Raylib.GetScreenHeight());
        Raylib.DrawText(String.format("Volume1: %s", this.volume), 24, 32, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume2: %s", this.volume2), 24, 56, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume3: %s", this.volume3), 24, 80, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume4: %s", this.volume4), 24, 104, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Pause Tick: %s", this.ticks), 24, 256, 24, Colors.MAGENTA);
        int posX = (Raylib.GetScreenWidth() / 2) - (Raylib.MeasureText("Paused", 48) / 2);
        int posY = (Raylib.GetScreenHeight() / 2) - 24;
        Raylib.DrawText("Paused", posX, posY, 48, Colors.WHITE);
        super.render();
    }

    @Override
    public void onReplacedOrRemoved() {
        Raylib.StopMusicStream(this.music);
        Raylib.StopMusicStream(this.music2);
        Raylib.StopMusicStream(this.music3);
        Raylib.StopMusicStream(this.music4);
    }

    @Override
    public void onGameClose() {
        if (this.music != null) Raylib.UnloadMusicStream(this.music);
        if (this.music2 != null) Raylib.UnloadMusicStream(this.music2);
        if (this.music3 != null) Raylib.UnloadMusicStream(this.music3);
        if (this.music4 != null) Raylib.UnloadMusicStream(this.music4);
    }
}

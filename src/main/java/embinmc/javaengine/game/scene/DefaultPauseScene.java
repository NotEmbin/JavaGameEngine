package embinmc.javaengine.game.scene;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.example.ExampleObject;
import embinmc.javaengine.text.Text;
import embinmc.javaengine.util.CoordHelper;

public class DefaultPauseScene extends Scene implements DoOnGameClose {
    private Raylib.Music music, music2, music3, music4, music5;
    private float volume, volume2, volume3, volume4, volume5;
    private Raylib.Rectangle bg;
    private Text pauseText;

    public DefaultPauseScene() {
        this.addObject(new ExampleObject());
    }

    @Override
    public void init() {
        this.ticks = 0;
        this.pauseText = Text.literal("Paused").setSize(5).setAlignment(Text.Alignment.CENTER);
        if (this.music == null) this.music = Raylib.LoadMusicStream("assets/engine/music/pause1.wav");
        if (this.music2 == null) this.music2 = Raylib.LoadMusicStream("assets/engine/music/pause2.wav");
        if (this.music3 == null) this.music3 = Raylib.LoadMusicStream("assets/engine/music/pause3.wav");
        if (this.music4 == null) this.music4 = Raylib.LoadMusicStream("assets/engine/music/pause4.wav");
        if (this.music5 == null) this.music5 = Raylib.LoadMusicStream("assets/engine/music/pause5.wav");
        Raylib.SetMusicVolume(this.music, 0.0f);
        Raylib.SetMusicVolume(this.music2, 0.0f);
        Raylib.SetMusicVolume(this.music3, 0.001f);
        Raylib.SetMusicVolume(this.music4, 0.001f);
        Raylib.SetMusicVolume(this.music5, 0.001f);
        Raylib.PlayMusicStream(this.music);
        Raylib.PlayMusicStream(this.music2);
        Raylib.PlayMusicStream(this.music3);
        Raylib.PlayMusicStream(this.music4);
        Raylib.PlayMusicStream(this.music5);
        this.bg = new Raylib.Rectangle().x(0).y(0).width(Raylib.GetScreenWidth()).height(Raylib.GetScreenHeight());
    }

    @Override
    public void update() {
        super.update();
        this.volume = Math.clamp(((float) this.ticks - 300) / 2000, 0.0f, 0.25f);
        this.volume2 = Math.clamp(((float) this.ticks - 3000) / 2000, 0.0f, 0.2f);
        this.volume3 = Math.clamp(((float) this.ticks - 6000) / 2000, 0.001f, 0.2f);
        this.volume4 = Math.clamp(((float) this.ticks - 9000) / 2000, 0.001f, 0.2f);
        this.volume5 = Math.clamp(((float) this.ticks - 11500) / 2000, 0.001f, 0.2f);
        //this.volume2 = Math.clamp(((float) this.ticks - 2000) / 2000, 0.0f, 0.2f);
        //this.volume3 = Math.clamp(((float) this.ticks - 3000) / 2000, 0.001f, 0.2f); // quickmode
        //this.volume4 = Math.clamp(((float) this.ticks - 4000) / 2000, 0.001f, 0.2f);
        //this.volume5 = Math.clamp(((float) this.ticks - 5000) / 2000, 0.001f, 0.2f);
        Raylib.SetMusicVolume(this.music, this.volume);
        Raylib.SetMusicVolume(this.music2, this.volume2);
        Raylib.SetMusicVolume(this.music3, this.volume3);
        Raylib.SetMusicVolume(this.music4, this.volume4);
        Raylib.SetMusicVolume(this.music5, this.volume5);
        Raylib.UpdateMusicStream(this.music);
        Raylib.UpdateMusicStream(this.music2);
        Raylib.UpdateMusicStream(this.music3);
        Raylib.UpdateMusicStream(this.music4);
        Raylib.UpdateMusicStream(this.music5);
    }

    @Override
    public void render() {
        Raylib.DrawRectangleRec(this.bg, Raylib.GetColor(0x66222288));
        //Raylib.DrawRectangle(0, 0, Raylib.GetScreenWidth(), Raylib.GetScreenHeight());
        Raylib.DrawText(String.format("Volume1: %s", this.volume), 24, 32, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume2: %s", this.volume2), 24, 56, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume3: %s", this.volume3), 24, 80, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume4: %s", this.volume4), 24, 104, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Volume5: %s", this.volume5), 24, 128, 24, Colors.MAGENTA);
        Raylib.DrawText(String.format("Pause Tick: %s", this.ticks), 24, 256, 24, Colors.MAGENTA);
        //int posX = (Raylib.GetScreenWidth() / 2) - (Raylib.MeasureText("Paused", 48) / 2);
        //int posY = (Raylib.GetScreenHeight() / 2) - 24;
        //Raylib.DrawText("Paused", posX, posY, 48, Colors.WHITE);
        this.pauseText.render(CoordHelper.getCenterX(), CoordHelper.getCenterY());
        super.render();
    }

    @Override
    public void onReplacedOrRemoved() {
        Raylib.StopMusicStream(this.music);
        Raylib.StopMusicStream(this.music2);
        Raylib.StopMusicStream(this.music3);
        Raylib.StopMusicStream(this.music4);
        Raylib.StopMusicStream(this.music5);
    }

    @Override
    public void onGameClose() {
        if (this.music != null) Raylib.UnloadMusicStream(this.music);
        if (this.music2 != null) Raylib.UnloadMusicStream(this.music2);
        if (this.music3 != null) Raylib.UnloadMusicStream(this.music3);
        if (this.music4 != null) Raylib.UnloadMusicStream(this.music4);
        if (this.music5 != null) Raylib.UnloadMusicStream(this.music5);
    }
}

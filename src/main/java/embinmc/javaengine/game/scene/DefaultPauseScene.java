package embinmc.javaengine.game.scene;

import com.raylib.Colors;
import com.raylib.Raylib;
import embinmc.javaengine.Engine;
import embinmc.javaengine.example.ExampleObject;
import embinmc.javaengine.game.scene.object.ButtonObject;
import embinmc.javaengine.game.scene.object.ClickableObject;
import embinmc.javaengine.game.scene.object.GameObject;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.text.Fonts;
import embinmc.javaengine.text.Text;
import embinmc.javaengine.util.CoordHelper;

public class DefaultPauseScene extends Scene implements DoOnGameClose {
    private Raylib.Music music, music2, music3, music4, music5;
    private float volume, volume2, volume3, volume4, volume5;
    private Raylib.Rectangle bg;
    private Text pauseText;
    private Text verText;

    public DefaultPauseScene() {
        this.addObject(new ExampleObject());

        this.addObject(new ButtonObject( // unpause game
                Identifier.ofEngine("ui/button"),
                Identifier.ofEngine("ui/button_hover"),
                Text.literal("Back to Game").setSize(3).setAlignment(Text.Alignment.CENTER)
        ).setClickAction((_, engine, _) -> engine.getGame().unpause()));

        this.addObject(new ButtonObject( // quit game
                Identifier.ofEngine("ui/button"),
                Identifier.ofEngine("ui/button_hover"),
                Text.literal("Quit Game").setSize(3).setAlignment(Text.Alignment.CENTER)
        ).setClickAction((_, _, _) -> this.setSubScene(new ConfirmExitScene())));
    }

    @Override
    public void init() {
        this.ticks = 0;
        this.pauseText = Text.literal("Paused").setSize(5).setAlignment(Text.Alignment.CENTER);
        String vtext = String.format("%s %s", Engine.getInstance().getGame().getGameArguments().gameName(), Engine.getInstance().getGame().getVersion());
        this.verText = Text.literal(vtext).setSize(2).setAlignment(Text.Alignment.RIGHT).setColor(Colors.GRAY);
        if (this.music == null) this.music = Raylib.LoadMusicStream("assets/engine/music/pause1.wav");
        if (this.music2 == null) this.music2 = Raylib.LoadMusicStream("assets/engine/music/pause2.wav");
        if (this.music3 == null) this.music3 = Raylib.LoadMusicStream("assets/engine/music/pause3.wav");
        if (this.music4 == null) this.music4 = Raylib.LoadMusicStream("assets/engine/music/pause4.wav");
        if (this.music5 == null) this.music5 = Raylib.LoadMusicStream("assets/engine/music/pause5.wav");
        Raylib.SetMusicVolume(this.music, 0.0f);
        Raylib.SetMusicVolume(this.music2, 0.0f);
        Raylib.SetMusicVolume(this.music3, 0.001f); // keep min above 0 so the sound actually keeps playing at all times
        Raylib.SetMusicVolume(this.music4, 0.001f);
        Raylib.SetMusicVolume(this.music5, 0.001f); // if volume is 0 for too long, the sound stops playing and sounds get desynced
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
        this.bg.width(Raylib.GetScreenWidth()).height(Raylib.GetScreenHeight());
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
        if (!this.isInSubScene()) {
            this.gameObjects.get(1).setX(CoordHelper.getCenterX()).setY(CoordHelper.fromCenterY(16));
            this.gameObjects.get(2).setX(CoordHelper.getCenterX()).setY(CoordHelper.fromBottom(64));
        }
    }

    @Override
    public void render() {
        Raylib.DrawRectangleRec(this.bg, Raylib.GetColor(0x66222288));
        if (!this.isInSubScene()) {
            Raylib.DrawText(String.format("Volume1: %s", this.volume), 24, 32, 20, Colors.MAGENTA);
            Raylib.DrawText(String.format("Volume2: %s", this.volume2), 24, 56, 20, Colors.MAGENTA);
            Raylib.DrawText(String.format("Volume3: %s", this.volume3), 24, 80, 20, Colors.MAGENTA);
            Raylib.DrawText(String.format("Volume4: %s", this.volume4), 24, 104, 20, Colors.MAGENTA);
            Raylib.DrawText(String.format("Volume5: %s", this.volume5), 24, 128, 20, Colors.MAGENTA);
            Raylib.DrawText(String.format("Pause Tick: %s", this.ticks), 24, 256, 30, Colors.MAGENTA);
            this.pauseText.render(CoordHelper.getCenterX(), CoordHelper.fromCenterY(-100));
            this.verText.render(CoordHelper.fromRight(8), CoordHelper.fromBottom((int) (this.verText.getHeight() - 4)));
        }
        super.render();
    }

    @Override
    public void onReplacedOrRemoved() {
        if (this.isInSubScene()) {
            this.exitSubScene();
        }
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

    protected static class ConfirmExitScene extends Scene {
        protected ClickableObject confirmButton;
        protected ClickableObject cancelButton;
        protected Text header;

        @Override
        public void init() {
            this.confirmButton = new ButtonObject(
                    Identifier.ofEngine("ui/button"),
                    Identifier.ofEngine("ui/button_hover"),
                    Text.literal("Confirm").setSize(3).setAlignment(Text.Alignment.CENTER)
            ).setClickAction(((_, engine, _) -> engine.getGame().closeGame()));
            this.cancelButton = new ButtonObject(
                    Identifier.ofEngine("ui/button"),
                    Identifier.ofEngine("ui/button_hover"),
                    Text.literal("Cancel").setSize(3).setAlignment(Text.Alignment.CENTER)
            ).setClickAction(((_, _, _) -> this.parentScene.exitSubScene()));
            this.header = Text.literal("Are you sure you want to quit?").setAlignment(Text.Alignment.CENTER).setSize(3);
        }

        @Override
        public void update() {
            super.update();
            this.confirmButton.setX(CoordHelper.getCenterX()).setY(CoordHelper.fromCenterY(-16)).update();
            this.cancelButton.setX(CoordHelper.getCenterX()).setY(CoordHelper.fromCenterY(48)).update();
        }

        @Override
        public void render() {
            super.render();
            this.confirmButton.render();
            this.cancelButton.render();
            this.header.render(CoordHelper.getCenterX(), CoordHelper.fromCenterY(-96));
        }

        @Override
        public void onReplacedOrRemoved() {
        }
    }
}

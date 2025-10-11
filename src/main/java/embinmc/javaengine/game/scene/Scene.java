package embinmc.javaengine.game.scene;

import embinmc.javaengine.game.scene.object.GameObject;
import embinmc.javaengine.resource.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Scene {
    public List<GameObject> gameObjects = new ArrayList<>();
    public Identifier backgroundTextureId;
    protected boolean pauseable = true;
    protected int ticks = 0;

    public abstract void init();

    public void addObject(GameObject object) {
        this.gameObjects.add(object);
    }

    public void update() {
        for (GameObject object : this.gameObjects) {
            object.update();
        }
        this.ticks++;
    }

    public void render() {
        for (GameObject object : this.gameObjects) {
            object.render();
        }
    }

    public abstract void onReplacedOrRemoved();

    public boolean isScenePauseable() {
        return this instanceof PauseableScene;
    }

    public int currentTick() {
        return this.ticks;
    }
}

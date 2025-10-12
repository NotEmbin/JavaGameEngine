package embinmc.javaengine.game.scene;

import embinmc.javaengine.game.scene.object.GameObject;
import embinmc.javaengine.render.Sprite;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

import java.util.*;

public abstract class Scene {
    protected Logger logger = Util.getLogger();
    public List<GameObject> gameObjects = new ArrayList<>();
    protected Scene currentSubScene = null;
    protected Scene parentScene = null;
    public Identifier backgroundTextureId;
    public Sprite backgroundTexture;
    protected boolean pauseable = true;
    protected int ticks = 0;

    public abstract void init();

    public void addObject(GameObject object) {
        this.gameObjects.add(object);
    }

    public void update() {
        if (this.isInSubScene()) {
            this.currentSubScene.update();
        } else {
            for (GameObject object : this.gameObjects) {
                object.update();
            }
            this.ticks++;
        }
    }

    public void render() {
        if (this.isInSubScene()) {
            this.currentSubScene.render();
        } else {
            for (GameObject object : this.gameObjects) {
                object.render();
            }
        }
    }

    public abstract void onReplacedOrRemoved();

    public boolean isScenePauseable() {
        return this instanceof PauseableScene;
    }

    public void exitSubScene() {
        if (!this.isInSubScene()) {
            this.logger.warn("Attempted to exit sub scene while not in one!");
            return;
        }
        this.currentSubScene.onReplacedOrRemoved();
        this.currentSubScene = null;
    }

    public boolean isInSubScene() {
        return this.currentSubScene != null;
    }

    public int currentTick() {
        return this.ticks;
    }

    public Scene setSubScene(Scene scene) {
        this.currentSubScene = scene.setParentScene(this);
        return this.currentSubScene;
    }

    public boolean hasParentScene() {
        return this.parentScene != null;
    }

    public Scene setParentScene(Scene scene) {
        this.parentScene = scene;
        return this;
    }
}

package embinmc.javaengine.game.scene;

import embinmc.javaengine.game.scene.object.GameObject;
import embinmc.javaengine.resource.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    public List<GameObject> gameObjects = new ArrayList<>();
    public Identifier backgroundTextureId;
    public boolean pauseable;

    public abstract void init();

    public void addObject(GameObject object) {
        this.gameObjects.add(object);
    }

    public void update() {
        for (GameObject object : this.gameObjects) {
            object.update();
        }
    }

    public void render() {
        for (GameObject object : this.gameObjects) {
            object.render();
        }
    }
}

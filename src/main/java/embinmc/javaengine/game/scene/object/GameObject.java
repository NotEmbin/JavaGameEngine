package embinmc.javaengine.game.scene.object;

import embinmc.javaengine.resource.Identifier;

public abstract class GameObject {
    public Identifier textureId;
    public int width;
    public int height;

    public GameObject(Identifier textureId, int width, int height) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
    }

    public abstract void render();
    public abstract void update();
}

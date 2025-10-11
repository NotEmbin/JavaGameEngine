package embinmc.javaengine.render;

import com.raylib.Raylib;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.resource.ResourceType;

public class JeTexture {
    public Identifier identifier;
    public Raylib.Texture texture;

    private JeTexture(Identifier id, Raylib.Texture texture) {
        this.identifier = id;
        this.texture = texture;
    }

    public static JeTexture of(Identifier id) {
        String imagePath = texturePathFromId(id);
        Raylib.Texture newTexture = Raylib.LoadTexture(imagePath);
        JeTexture jeTexture = new JeTexture(id, newTexture);
        return TextureManager.getManager().loadThisTexture(jeTexture);
    }

    public static JeTexture supply(Identifier id, Raylib.Texture texture) {
        return new JeTexture(id, texture);
    }

    public static String texturePathFromId(Identifier identifier) {
        String formattedPath = "textures/" + identifier.getPath() + ".png";
        Identifier formattedId = Identifier.of(identifier.getNamespace(), formattedPath);
        return ResourceType.ASSET.pathFromIdentifier(formattedId);
    }
}

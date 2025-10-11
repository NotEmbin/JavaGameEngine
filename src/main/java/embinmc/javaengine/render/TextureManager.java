package embinmc.javaengine.render;

import com.raylib.Raylib;
import embinmc.javaengine.resource.Identifier;
import embinmc.javaengine.util.Util;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.MAGENTA;

public class TextureManager {
    private static final TextureManager INSTANCE = new TextureManager();
    public final JeTexture missingnoTexture;
    public Map<String, JeTexture> mappedTextures;
    private final Logger logger;

    public TextureManager() {
        this.mappedTextures = HashMap.newHashMap(64);
        this.logger = Util.getLogger();
        Raylib.Image missingno = Raylib.GenImageChecked(16, 16, 8, 8, BLACK, MAGENTA);
        JeTexture missingnoJe = JeTexture.supply(Identifier.ofEngine("missingno"), Raylib.LoadTextureFromImage(missingno));
        this.mappedTextures.put(missingnoJe.identifier.toString(), missingnoJe);
        this.missingnoTexture = missingnoJe;
    }

    public static TextureManager getManager() {
        return TextureManager.INSTANCE;
    }

    public JeTexture loadThisTexture(JeTexture texture) {
        this.mappedTextures.put(texture.identifier.toString(), texture);
        return texture;
    }

    public Identifier getMissingnoId() {
        return this.missingnoTexture.identifier;
    }

    public JeTexture getOrLoadTexture(Identifier id) {
        this.validateFallbackExists();
        if (this.textureLoaded(id)) {
            return this.mappedTextures.get(id.toString());
        } else {
            try {
                return JeTexture.of(id);
            } catch (Exception e) {
                return this.mappedTextures.get(this.getMissingnoId().toString());
            }
        }
    }

    public JeTexture getTexture(Identifier id) {
        this.validateFallbackExists();
        return this.mappedTextures.getOrDefault(id.toString(), this.missingnoTexture);
    }

    public boolean textureLoaded(Identifier id) {
        return this.mappedTextures.containsKey(id.toString());
    }

    public boolean textureNotLoaded(Identifier id) {
        return !this.textureLoaded(id);
    }

    public void unloadTexture(Identifier id) {
        if (id.toString().equals(this.getMissingnoId().toString())) {
            this.logger.error("Illegal attempt to unload the fallback texture");
            return;
        }
        if (this.textureLoaded(id)) {
            this.logger.info("Unloading texture: {}", id);
            JeTexture jeTexture = this.mappedTextures.get(id.toString());
            this.mappedTextures.remove(id.toString());
            Raylib.UnloadTexture(jeTexture.texture);
        } else {
            this.logger.warn("Cannot unload {}, as it (probably) isn't loaded in the first place!", id);
        }
    }

    public void validateFallbackExists() {
        //if (this.textureNotLoaded(MISSINGNO)) throw new IllegalStateException("Fallback texture is not loaded!");
    }
}

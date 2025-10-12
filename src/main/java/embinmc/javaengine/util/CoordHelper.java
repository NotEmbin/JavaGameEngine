package embinmc.javaengine.util;

import com.raylib.Raylib;

public final class CoordHelper {
    public static int getCenterX() {
        return Raylib.GetScreenWidth() / 2;
    }

    public static int getCenterY() {
        return Raylib.GetScreenHeight() / 2;
    }
    public static int fromCenterX(int offset) {
        return getCenterX() + offset;
    }

    public static int fromCenterY(int offset) {
        return getCenterY() + offset;
    }

    public static int fromRight(int awayFrom) {
        return Raylib.GetScreenWidth() - awayFrom;
    }

    public static int fromBottom(int awayFrom) {
        return Raylib.GetScreenHeight() - awayFrom;
    }

    public static int fromLeft(int awayFrom) { // #notusingts :troll:
        return awayFrom;
    }

    public static int fromTop(int awayFrom) {
        return awayFrom;
    }
}

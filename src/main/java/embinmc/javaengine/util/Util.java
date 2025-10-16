package embinmc.javaengine.util;

import com.raylib.Raylib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Gets a logger for the class that is calling this method
     * @return Logger for class this method is called in
     */
    public static Logger getLogger() {
        return LoggerFactory.getLogger(STACK_WALKER.getCallerClass());
    }

    /**
     * Cuts a value off the end of a string
     * <p>
     * Returns <code>string</code> unmodified if <code>string</code> doesn't end with <code>end</code>
     *
     * @param string String to modify
     * @param end Value to remove from <code>string</code>
     * @return <code>string</code> with <code>end</code> removed from the end of it
     */
    public static String removeEndFromString(String string, String end) {
        if (string.endsWith(end)) return string.substring(0, string.length() - end.length());
        return string;
    }
    public static String removeStartFromString(String string, String start) {
        if (string.endsWith(start)) return string.substring(start.length());
        return string;
    }

    /**
     * <p>Repeats a void method a specified amount of times.</p>
     * <p>The supplied integer is the index.</p>
     * <p>Index starts at 1.</p>
     */
    public static void repeat(int amount, SuppliedVoid<Integer> action) {
        for (int index = 1; index <= amount; index++) {
            action.execute(index);
        }
    }

    public static boolean probablyBuilt() {
        return Raylib.GetWorkingDirectory().getString().endsWith("\\bin");
    }
}

package embinmc.javaengine.util;

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
}

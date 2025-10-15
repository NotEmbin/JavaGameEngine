package embinmc.javaengine.util;

@FunctionalInterface
public interface SuppliedVoid<T> {
    void execute(T param);
}

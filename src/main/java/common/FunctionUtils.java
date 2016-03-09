package common;

import java.util.function.Consumer;

public abstract class FunctionUtils {
    public static <T> Consumer<T> emptyConsumer() {
        return (arg) -> {
        };
    }
}

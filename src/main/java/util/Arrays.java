package util;

import java.util.Objects;

public final class Arrays {

    public static <T> void requireNonEmpty(T[] array, String message) {
        Objects.requireNonNull(array, message);
        if (array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

}

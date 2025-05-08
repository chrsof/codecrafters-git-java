package util;

import java.util.Objects;

public final class Arrays {

    public static <T> void requireNonEmpty(T[] array, String message) {
        Objects.requireNonNull(array, message);
        if (array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void requireSize(T[] array, int size, String message) {
        Objects.requireNonNull(array, message);
        if (array.length != size) {
            throw new IllegalArgumentException(message);
        }
    }

}

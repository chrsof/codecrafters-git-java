package util;

import java.util.Objects;

public final class Strings {

    public static void requireLength(String string, int length) {
        string = Objects.requireNonNullElse(string, "");
        if (string.length() != length) {
            throw new IllegalArgumentException("Required string of length %d, got %d".formatted(length, string.length()));
        }
    }

    public static void requireNonBlank(String string, String message) {
        Objects.requireNonNull(string, message);
        if (string.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireEquals(String actual, String expected, String message) {
        Objects.requireNonNull(actual, message);
        Objects.requireNonNull(expected, message);
        if (!expected.equals(actual)) {
            throw new IllegalArgumentException(message);
        }
    }

}

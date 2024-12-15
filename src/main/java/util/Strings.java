package util;

import java.util.Objects;

public final class Strings {

    public static void requireLength(String str, int length) {
        str = Objects.requireNonNullElse(str, "");
        if (str.length() != length) {
            throw new IllegalArgumentException("Required string of length %d, got %d.".formatted(length, str.length()));
        }
    }

    public static void requireNonBlank(String value, String message) {
        Objects.requireNonNull(value, message);
        if (value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireEquals(String value, String expectedValue, String message) {
        Objects.requireNonNull(value, message);
        Objects.requireNonNull(expectedValue, message);
        if (!expectedValue.equals(value)) {
            throw new IllegalArgumentException(message);
        }
    }

}

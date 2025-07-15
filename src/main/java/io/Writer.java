package io;

import java.io.IOException;
import java.nio.file.Path;

public interface Writer {
    default String write(Path path) throws IOException {
        return "";
    }

    default String write(String... args) throws IOException {
        return "";
    }
}

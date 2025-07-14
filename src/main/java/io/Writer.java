package io;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface Writer {
    String write(Path path) throws IOException;
}

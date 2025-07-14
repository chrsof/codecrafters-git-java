package io;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface Reader {
    String read(Path path) throws IOException;
}

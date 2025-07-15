package io;

import java.io.IOException;
import java.nio.file.Path;

public final class WriterFactory {

    public static String write(Path path, ObjectType objectType, String... args) throws IOException {
        return switch (objectType) {
            case BLOB -> BlobWriter.getInstance().write(path);
            case TREE -> TreeWriter.getInstance().write(path);
            case COMMIT -> CommitWriter.getInstance().write(args);
        };
    }

}

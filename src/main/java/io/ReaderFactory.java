package io;

import java.io.IOException;
import java.nio.file.Path;

public final class ReaderFactory {

    public static String read(Path path, ObjectType objectType) throws IOException {
        return switch (objectType) {
            case BLOB -> BlobReader.getInstance().read(path);
            case TREE -> TreeReader.getInstance().read(path);
        };
    }

}

package io;

import util.Regex;
import util.Zlib;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;

public final class BlobReader implements Reader {

    private static final class LazyHolder {
        private static final BlobReader INSTANCE = new BlobReader();
    }

    private BlobReader() {
    }

    public static BlobReader getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public String read(Path path) throws IOException {
        byte[] compressedData = Files.readAllBytes(path);
        byte[] decompressedData = Zlib.decompress(compressedData);

        Matcher matcher = Regex.BLOB_OBJECT.get().matcher(new String(decompressedData, StandardCharsets.UTF_8));

        return matcher.find() ? matcher.group("content") : "";
    }

}

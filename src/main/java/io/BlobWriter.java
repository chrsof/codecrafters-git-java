package io;

import util.PathFactory;
import util.Strings;
import util.Zlib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class BlobWriter implements Writer {

    private static final class LazyHolder {
        private static final BlobWriter INSTANCE = new BlobWriter();
    }

    private BlobWriter() {
    }

    public static BlobWriter getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public String write(Path path) throws IOException {
        byte[] content = Files.readAllBytes(path);

        // blob <size>\0<content>
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("blob %d"
                .formatted(content.length)
                .getBytes(StandardCharsets.UTF_8));
        baos.write(0);
        baos.write(content);

        byte[] decompressedData = baos.toByteArray();
        byte[] compressedData = Zlib.compress(decompressedData);

        String hash = Strings.toSHA1(decompressedData);

        PathFactory.writeToGitObjects(hash, compressedData);

        return hash;
    }

}

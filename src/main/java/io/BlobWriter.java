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
        String blobContent = "blob %d\0".formatted(content.length);

        // blob <size>\0<content>
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(blobContent.getBytes(StandardCharsets.UTF_8));
        baos.write(content);

        byte[] decompressedData = baos.toByteArray();
        byte[] compressedData = Zlib.compress(decompressedData);

        String hash = Strings.toSHA1(decompressedData);

        Path blobParentDirPath = Files.createDirectory(PathFactory.getGitObjectsPath().resolve(hash.substring(0, 2)));
        Path blobObjectPath = Files.createFile(blobParentDirPath.resolve(hash.substring(2)));
        Files.write(blobObjectPath, compressedData);

        return hash;
    }

}

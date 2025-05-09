package git;

import util.PathFactory;
import util.Strings;
import util.Zlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteBlobObjectCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        Path path = Path.of(args[0]);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File %s not found.".formatted(path.toAbsolutePath()));
        }

        String content = Files.readString(path);
        String blobContent = "blob %d\0%s".formatted(content.length(), content);

        byte[] decompressedData = blobContent.getBytes(StandardCharsets.UTF_8);
        byte[] compressedData = Zlib.compress(decompressedData);

        String hash = Strings.toSHA1(decompressedData);

        Path blobParentDirPath = Files.createDirectory(PathFactory.getGitObjectsPath().resolve(hash.substring(0, 2)));
        Path blobObjectPath = Files.createFile(blobParentDirPath.resolve(hash.substring(2)));
        Files.write(blobObjectPath, compressedData);

        System.out.print(hash);
    }

}

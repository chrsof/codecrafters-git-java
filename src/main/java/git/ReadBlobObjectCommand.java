package git;

import util.PathFactory;
import util.Regex;
import util.Strings;
import util.Zlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;

public class ReadBlobObjectCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        String hash = args[0];
        Strings.requireLength(hash, 40);

        Path path = PathFactory.getGitObjectsPath().resolve(hash.substring(0, 2), hash.substring(2));
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Blob %s not found.".formatted(hash));
        }

        byte[] compressedData = Files.readAllBytes(path);
        byte[] decompressedData = Zlib.decompress(compressedData);

        Matcher matcher = Regex.BLOB_OBJECT.get().matcher(new String(decompressedData, StandardCharsets.UTF_8));
        if (matcher.find()) {
            String content = matcher.group("content");
            System.out.print(content);
        }
    }

}

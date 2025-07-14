package git;

import io.ObjectType;
import io.ReaderFactory;
import util.PathFactory;
import util.Strings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadBlobObjectCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        String hash = args[0];
        Strings.requireLength(hash, 40);
        Path path = PathFactory.getGitObjectsPath().resolve(hash.substring(0, 2), hash.substring(2));
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Blob %s not found.".formatted(hash));
        }
        String content = ReaderFactory.read(path, ObjectType.BLOB);
        System.out.print(content);
    }

}

package git;

import io.ObjectType;
import io.ReaderFactory;
import util.PathFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadTreeObjectCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        String hash = args[0];
        Path path = PathFactory.getGitObjectsPath().resolve(hash.substring(0, 2), hash.substring(2));
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File %s is not found.".formatted(hash));
        }
        String content = ReaderFactory.read(path, ObjectType.TREE);
        System.out.println(content);
    }

}

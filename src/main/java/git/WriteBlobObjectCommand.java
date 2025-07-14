package git;

import io.ObjectType;
import io.WriterFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteBlobObjectCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        Path path = Path.of(args[0]);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File %s not found.".formatted(path.toAbsolutePath()));
        }
        String hash = WriterFactory.write(path, ObjectType.BLOB);
        System.out.print(hash);
    }

}

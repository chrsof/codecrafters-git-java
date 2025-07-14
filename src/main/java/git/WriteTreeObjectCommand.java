package git;

import io.ObjectType;
import io.WriterFactory;
import util.PathFactory;

import java.io.IOException;
import java.nio.file.Path;

public class WriteTreeObjectCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        Path path = PathFactory.getRootPath();
        String hash = WriterFactory.write(path, ObjectType.TREE);
        System.out.print(hash);
    }

}

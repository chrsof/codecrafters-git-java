package git;

import io.ObjectType;
import io.WriterFactory;

import java.io.IOException;

public class CommitTreeCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        String hash = WriterFactory.write(null, ObjectType.COMMIT, args);
        System.out.print(hash);
    }

}

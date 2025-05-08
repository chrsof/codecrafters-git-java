package git;

import java.io.IOException;

@FunctionalInterface
public interface Command {
    void execute(String... args) throws IOException;
}

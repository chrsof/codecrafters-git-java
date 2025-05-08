package git;

import java.io.IOException;

public class CommandExecutor {

    public void execute(Command command, String... args) throws IOException {
        command.execute(args);
    }

}

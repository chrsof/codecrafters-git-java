package git;

import java.util.Arrays;
import java.util.Optional;

public enum GitCommand {
    INIT("init"),
    CAT_FILE("cat-file");

    private final String command;

    GitCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Optional<GitCommand> resolveCommand(String command) {
        return Arrays.stream(GitCommand.values())
                .filter(cmd -> cmd.getCommand().equals(command))
                .findFirst();
    }

}

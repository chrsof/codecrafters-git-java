import git.*;
import util.Arrays;
import util.Validator;

import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {
        Arrays.requireNonEmpty(args, "Invalid input: usage <command> <options> <args>");

        Optional<GitCommand> command = GitCommand.resolveCommand(args[0]);
        if (command.isEmpty()) {
            throw new UnsupportedOperationException("Unknown command: %s".formatted(args[0]));
        }

        CommandExecutor executor = new CommandExecutor();

        switch (command.get()) {
            case INIT -> executor.execute(new InitCommand());
            case CAT_FILE -> {
                Validator.validateBlobObjectRead(args);
                executor.execute(new ReadBlobObjectCommand(), args[2]);
            }
            case HASH_OBJECT -> {
                Validator.validateBlobObjectWrite(args);
                executor.execute(new WriteBlobObjectCommand(), args[2]);
            }
        }
    }

}

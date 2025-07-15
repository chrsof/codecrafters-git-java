import git.*;
import util.Arrays;
import util.Validator;

import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Arrays.requireNonEmpty(args, "Usage: <command> <options> <args>");

        Optional<GitCommand> command = GitCommand.resolveCommand(args[0]);
        if (command.isEmpty()) {
            throw new UnsupportedOperationException("Unknown command: %s".formatted(args[0]));
        }

        CommandExecutor commandExecutor = new CommandExecutor();

        try {
            switch (command.get()) {
                case INIT -> commandExecutor.execute(new InitCommand());
                case CAT_FILE -> {
                    Validator.validateBlobObjectRead(args);
                    commandExecutor.execute(new ReadBlobObjectCommand(), args[2]);
                }
                case HASH_OBJECT -> {
                    Validator.validateBlobObjectWrite(args);
                    commandExecutor.execute(new WriteBlobObjectCommand(), args[2]);
                }
                case LS_TREE -> {
                    Validator.validateTreeObjectRead(args);
                    commandExecutor.execute(new ReadTreeObjectCommand(), args[2]);
                }
                case WRITE_TREE -> commandExecutor.execute(new WriteTreeObjectCommand());
                case COMMIT_TREE -> {
                    Validator.validateTreeCommit(args);
                    commandExecutor.execute(new CommitTree(), args[1], args[3], args[5]);
                }
            }
        } catch (IOException ioe) {
            System.err.printf("IOException: %s%n", ioe.getMessage());
        }

    }

}

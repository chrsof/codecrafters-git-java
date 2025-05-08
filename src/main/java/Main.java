import git.CommandExecutor;
import git.GitCommand;
import git.InitCommand;
import git.ReadBlobObjectCommand;
import util.Arrays;
import util.Validator;

import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Arrays.requireNonEmpty(args, "Invalid input: usage <command> <options> <args>");

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
            }
        } catch (IOException ioe) {
            System.err.printf("IOException: %s%n", ioe.getMessage());
        }

    }

}

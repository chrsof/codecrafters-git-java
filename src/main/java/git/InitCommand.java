package git;

import util.PathFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InitCommand implements Command {

    @Override
    public void execute(String... args) throws IOException {
        Path git = Files.createDirectory(PathFactory.getGitRootPath());
        Files.createDirectory(PathFactory.getGitObjectsPath());
        Files.createDirectory(PathFactory.getGitRefsPath());
        Path head = PathFactory.getGitHeadPath();
        Files.createFile(head);
        Files.writeString(head, "ref: refs/heads/main\n");
        System.out.printf("Initialized empty Git repository in %s%n", git.toAbsolutePath());
    }

}

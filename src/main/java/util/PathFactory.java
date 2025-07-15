package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class PathFactory {

    private static final String DIRECTORY_ROOT_GIT = ".git";
    private static final String DIRECTORY_OBJECTS = "objects";
    private static final String DIRECTORY_REFS = "refs";
    private static final String FILE_HEAD = "HEAD";

    public static Path getGitRootPath() {
        return Path.of(DIRECTORY_ROOT_GIT);
    }

    public static Path getGitObjectsPath() {
        return getGitRootPath().resolve(DIRECTORY_OBJECTS);
    }

    public static Path getGitRefsPath() {
        return getGitRootPath().resolve(DIRECTORY_REFS);
    }

    public static Path getGitHeadPath() {
        return getGitRootPath().resolve(FILE_HEAD);
    }

    public static Path getRootPath() {
        return Path.of(System.getenv().get("PWD"));
    }

    public static void writeToGitObjects(String hash, byte[] data) throws IOException {
        Path parentDirPath = Files.createDirectory(PathFactory.getGitObjectsPath().resolve(hash.substring(0, 2)));
        Path objectPath = Files.createFile(parentDirPath.resolve(hash.substring(2)));
        Files.write(objectPath, data);
    }

}

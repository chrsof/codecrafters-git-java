package util;

import git.Mode;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class FileVisitor extends SimpleFileVisitor<Path> {

    private final Path root;
    private TreeNode tree;

    public FileVisitor(Path root, TreeNode tree) {
        this.root = root;
        this.tree = tree;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (dir.equals(root)) {
            return FileVisitResult.CONTINUE;
        }

        if (dir.startsWith(root.resolve(PathFactory.getGitRootPath()))) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        TreeNode node = new TreeNode(Mode.DIRECTORY, dir);
        tree.addNode(node);
        tree = node;

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        if (dir.equals(root)) {
            return FileVisitResult.CONTINUE;
        }

        tree = tree.getParent();

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isDirectory() || attrs.isOther()) {
            return FileVisitResult.CONTINUE;
        }

        tree.addNode(new TreeNode(attrs.isRegularFile() ? Mode.REGULAR_FILE : Mode.SYMBOLIC_LINK, file));

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        if (Objects.nonNull(exc)) {
            System.err.println(exc.getMessage());
        }

        return FileVisitResult.SKIP_SUBTREE;
    }

}

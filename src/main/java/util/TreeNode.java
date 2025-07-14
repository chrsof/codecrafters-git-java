package util;

import git.Mode;

import java.nio.file.Path;
import java.util.*;

public class TreeNode {

    private TreeNode parent;
    private final Mode mode;
    private final Path path;
    private final String name;
    private final TreeSet<TreeNode> children;

    public TreeNode(Mode mode, Path path) {
        Objects.requireNonNull(mode);
        Objects.requireNonNull(path);

        this.mode = mode;
        this.path = path;
        this.name = path.getFileName().toString();
        this.children = new TreeSet<>(Comparator.comparing(TreeNode::getName));
    }

    public void addNode(TreeNode node) {
        node.parent = this;
        children.add(node);
    }

    public Mode getMode() {
        return mode;
    }

    public Path getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeSet<TreeNode> getChildren() {
        return children;
    }

}

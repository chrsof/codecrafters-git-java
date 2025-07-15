package io;

import git.Mode;
import util.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TreeWriter implements Writer {

    private static final class LazyHolder {
        private static final TreeWriter INSTANCE = new TreeWriter();
    }

    private TreeWriter() {
    }

    public static TreeWriter getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public String write(Path path) throws IOException {
        TreeNode tree = new TreeNode(Mode.DIRECTORY, path);
        FileVisitor fileVisitor = new FileVisitor(path, tree);
        Files.walkFileTree(path, fileVisitor);
        return writeTree(tree);
    }

    private String writeTree(TreeNode tree) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (TreeNode node : tree.getChildren()) {
            String hash = switch (node.getMode()) {
                case REGULAR_FILE -> WriterFactory.write(node.getPath(), ObjectType.BLOB);
                case DIRECTORY -> writeTree(node);
                default -> throw new UnsupportedOperationException("Mode %s not supported.".formatted(node.getMode()));
            };

            // <mode> <name>\0<20_byte_sha>
            baos.write("%s %s"
                    .formatted(node.getMode().getMode(), node.getName())
                    .getBytes(StandardCharsets.UTF_8));
            baos.write(0);
            baos.write(Bytes.hexToBytes(hash));
        }

        // tree <size>\0
        byte[] entries = baos.toByteArray();

        baos = new ByteArrayOutputStream();
        baos.write("tree %d"
                .formatted(entries.length)
                .getBytes(StandardCharsets.UTF_8));
        baos.write(0);
        baos.write(entries);

        byte[] decompressedData = baos.toByteArray();
        byte[] compressedData = Zlib.compress(decompressedData);

        String hash = Strings.toSHA1(decompressedData);

        PathFactory.writeToGitObjects(hash, compressedData);

        return hash;
    }

}

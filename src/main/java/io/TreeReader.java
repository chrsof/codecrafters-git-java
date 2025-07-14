package io;

import git.Mode;
import util.Arrays;
import util.Regex;
import util.Zlib;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class TreeReader implements Reader {

    record TreeEntry(Mode mode, String name, byte[] hash) {
    }

    private static class LazyHolder {
        private static final TreeReader INSTANCE = new TreeReader();
    }

    private TreeReader() {
    }

    public static TreeReader getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public String read(Path path) throws IOException {
        byte[] compressedData = Files.readAllBytes(path);
        byte[] decompressedData = Zlib.decompress(compressedData);

        Objects.requireNonNull(decompressedData);

        TreeSet<TreeEntry> entries = new TreeSet<>(Comparator.comparing(TreeEntry::name));

        String[] parts = Regex.MODE_SEPARATOR.get().splitWithDelimiters(new String(decompressedData, StandardCharsets.UTF_8), -1);
        for (int i = 1; i < parts.length; i += 2) {
            String mode = parts[i];
            String entry = parts[i + 1];
            Mode.resolveMode(mode).ifPresent(m -> {
                String[] entryParts = Regex.NULL_SEPARATOR.get().split(entry);
                Arrays.requireSize(entryParts, 2, "Invalid file contents.");
                entries.add(new TreeEntry(m, entryParts[0].strip(), entryParts[1].strip().getBytes(StandardCharsets.UTF_8)));
            });
        }

        return entries.stream().map(TreeEntry::name).collect(Collectors.joining("\n"));
    }

}

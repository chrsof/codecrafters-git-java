package git;

import util.Arrays;
import util.PathFactory;
import util.Regex;
import util.Zlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

public class ReadTreeObjectCommand implements Command {

    record TreeEntry(Mode mode, String name, byte[] hash) {
    }

    @Override
    public void execute(String... args) throws IOException {
        String file = args[0];
        Path filepath = PathFactory.getGitObjectsPath().resolve(file.substring(0, 2), file.substring(2));
        if (!Files.exists(filepath)) {
            throw new FileNotFoundException("File %s is not found.".formatted(file));
        }

        byte[] data = Zlib.decompress(Files.readAllBytes(filepath));
        Objects.requireNonNull(data);

        TreeSet<TreeEntry> entries = new TreeSet<>(Comparator.comparing(TreeEntry::name));

        String[] parts = Regex.MODE_SEPARATOR.get().splitWithDelimiters(new String(data, StandardCharsets.UTF_8), -1);
        for (int i = 1; i < parts.length; i += 2) {
            String mode = parts[i];
            String entry = parts[i + 1];
            Mode.resolveMode(mode).ifPresent(m -> {
                String[] entryParts = Regex.NULL_SEPARATOR.get().split(entry);
                Arrays.requireSize(entryParts, 2, "Invalid file contents.");
                entries.add(new TreeEntry(m, entryParts[0].strip(), entryParts[1].strip().getBytes(StandardCharsets.UTF_8)));
            });
        }

        entries.stream().map(TreeEntry::name).forEach(System.out::println);
    }

}

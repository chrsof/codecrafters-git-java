package util;

public final class Validator {

    public static void validateBlobObjectRead(String... args) {
        String prompt = "Usage: cat-file -p <blob_sha>";
        Arrays.requireSize(args, 3, prompt);
        Strings.requireEquals(args[1], "-p", prompt);
        Strings.requireNonBlank(args[2], prompt);
    }

    public static void validateBlobObjectWrite(String... args) {
        String prompt = "Usage: hash-object -w <file>";
        Arrays.requireSize(args, 3, prompt);
        Strings.requireEquals(args[1], "-w", prompt);
        Strings.requireNonBlank(args[2], prompt);
    }

    public static void validateTreeObjectRead(String... args) {
        String prompt = "Usage: ls-tree --name-only <tree_sha>";
        Arrays.requireSize(args, 3, prompt);
        Strings.requireEquals(args[1], "--name-only", prompt);
        Strings.requireNonBlank(args[2], prompt);
    }

}

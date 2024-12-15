package util;

public final class Validator {

    public static void validateBlobObjectRead(String... args) {
        String prompt = "Usage cat-file -p <blob_sha>";
        Arrays.requireSize(args, 3, prompt);
        Strings.requireEquals(args[1], "-p", prompt);
        Strings.requireNonBlank(args[2], prompt);
    }

}
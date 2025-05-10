package git;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Mode {
    REGULAR_FILE("100644"),
    EXECUTABLE_FILE("100755"),
    SYMBOLIC_LINK("120000"),
    DIRECTORY("40000");

    private final String mode;

    Mode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public static String getAllModes() {
        return Arrays.stream(Mode.values())
                .map(Mode::getMode)
                .collect(Collectors.joining("|"));
    }

    public static Optional<Mode> resolveMode(String mode) {
        return Arrays.stream(Mode.values())
                .filter(m -> m.mode.equals(mode))
                .findFirst();
    }

}

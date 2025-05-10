package util;

import git.Mode;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Regex {

    public static final Supplier<Pattern> BLOB_OBJECT = () -> Pattern.compile("blob\\s\\d+\0(?<content>.*)");

    public static final Supplier<Pattern> MODE_SEPARATOR = () -> Pattern.compile("(%s)".formatted(Mode.getAllModes()));

    public static final Supplier<Pattern> NULL_SEPARATOR = () -> Pattern.compile("\\u0000");

}

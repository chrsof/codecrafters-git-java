package util;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Regex {

    public static final Supplier<Pattern> BLOB_OBJECT = () -> Pattern.compile("blob\\s\\d+\0(?<content>.*)");

}

package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

public final class Bytes {

    public static byte[] toSHA1(byte[] bytes) {
        Objects.requireNonNull(bytes);
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException(nsae);
        }
        return messageDigest.digest(bytes);
    }

    public static byte[] hexToBytes(String hex) {
        return HexFormat.of().parseHex(hex);
    }

}

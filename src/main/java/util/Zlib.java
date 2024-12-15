package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class Zlib {

    public static byte[] decompress(byte[] bytes) {
        Objects.requireNonNull(bytes);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Inflater inflater = new Inflater();
            inflater.setInput(bytes);

            byte[] buffer = new byte[512];

            int size;
            while (!inflater.finished()) {
                size = inflater.inflate(buffer);
                baos.write(buffer, 0, size);
            }

            return baos.toByteArray();
        } catch (IOException | DataFormatException e) {
            return new byte[0];
        }
    }

}

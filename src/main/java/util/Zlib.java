package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class Zlib {

    public static byte[] decompress(byte[] bytes) {
        Objects.requireNonNull(bytes);

        Inflater inflater = new Inflater();
        inflater.setInput(bytes);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
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

    public static byte[] compress(byte[] bytes) {
        Objects.requireNonNull(bytes);

        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[512];
            int size;
            while (!deflater.finished()) {
                size = deflater.deflate(buffer);
                baos.write(buffer, 0, size);
            }
            return baos.toByteArray();
        } catch (IOException ioe) {
            return new byte[0];
        }
    }

}
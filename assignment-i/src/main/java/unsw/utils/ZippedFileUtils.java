package unsw.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class ZippedFileUtils {
    /**
     * Zips the contents of a file.
     *
     * @param contents
     * @return the zipped file represented as a string
     */
    public static String zipFile(String contents) {
        if (contents == null || contents.length() == 0) {
            return null;
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(contents.getBytes());
            }
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a given string is already zipped.
     *
     * @param contents
     * @return true if the contents are zipped, false otherwise
     */
    public static boolean isZipped(String contents) {
        try {
            byte[] data = Base64.getDecoder().decode(contents);
            return isZipped(data);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isZipped(byte[] data) {
        return (data[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                && (data[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }

    /**
     * Unzips the contents of a file. Will simply return the contents if the
     * contents are not zipped.
     *
     * @param contents
     * @return the unzipped file
     */
    public static String unzipFile(String contents) {
        if (contents == null || contents.length() == 0) {
            return null;
        }

        if (!isZipped(contents)) {
            return contents;
        }

        StringBuilder out = new StringBuilder();

        byte[] data = Base64.getDecoder().decode(contents.getBytes());
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(data))) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzip))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    out.append(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out.toString();
    }
}

package org.heliosx.consultation.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class FileUtils {

    private FileUtils() {}

    public static String readFileFromResources(String filePath) {
        try (var resourceAsStream = FileUtils.class.getClassLoader().getResourceAsStream(filePath)) {
            if (resourceAsStream != null)
                return IOUtils.readLines(resourceAsStream, UTF_8).stream().reduce("", (acc, k) -> acc + k);
        } catch (IOException ex) {
            throw new RuntimeException("There was an exception when trying to load the file");
        }
        return null;
    }
}

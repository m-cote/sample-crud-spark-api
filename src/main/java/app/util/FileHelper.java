package app.util;

import app.Application;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FileHelper {

    private FileHelper() {
    }

    public static String extractResourceContent(String resourcePath) {

        try {
            URL url = Objects.requireNonNull(Application.class.getClassLoader().getResource(resourcePath));
            return IOUtils.toString(url.openStream());
        } catch (IOException | NullPointerException e ) {
            final String message = "Reading resource from path \"" + resourcePath + "\" failed: " + e;
            System.err.println(message);
            throw new RuntimeException(message);
        }

    }
}

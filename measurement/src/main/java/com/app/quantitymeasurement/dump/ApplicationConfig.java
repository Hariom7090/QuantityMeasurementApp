package com.app.quantitymeasurement.dump;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ApplicationConfig.class.getClassLoader()
                             .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
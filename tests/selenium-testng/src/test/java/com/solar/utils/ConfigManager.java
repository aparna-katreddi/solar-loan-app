package com.solar.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties props = new Properties();

    static {
        try {
            // Read environment from system property or env var
            String env = System.getProperty("env"); // e.g., mvn test -Denv=qa
            if (env == null) {
                env = System.getenv("ENV"); // fallback to ENV var
            }
            if (env == null) {
                env = "qa"; // default environment
            }
            String fileName = String.format("config.%s.properties", env);

        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("Could not find config.properties in classpath");
            }
        }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}


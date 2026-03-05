package com.java.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String TAX_RATE_KEY = "tax.rate";
    private static final String BASE_URL_KEY = "base.url";
    private static final double DEFAULT_TAX_RATE = 0.125;
    private static final String DEFAULT_BASE_URL =
            "https://equalexperts.github.io/backend-take-home-test-data/";

    private static Properties loadProperties() {

        try (InputStream input =
                     ConfigLoader.class.getClassLoader()
                             .getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            if (input != null) {
                properties.load(input);
            }
            return properties;
        } catch (Exception e) {
            return new Properties();
        }
    }

    public static double loadTaxRate() {
        Properties props = loadProperties();
        return Double.parseDouble(
                props.getProperty(TAX_RATE_KEY, String.valueOf(DEFAULT_TAX_RATE))
        );
    }

    public static String loadBaseUrl() {
        Properties props = loadProperties();
        return props.getProperty(BASE_URL_KEY, DEFAULT_BASE_URL);
    }
}

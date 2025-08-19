package utils;

import pages.ProductPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    static Properties properties;

    static {
        try{
            String path = "src/main/resources/config.properties";
            FileInputStream fileInputStream = new FileInputStream(path);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
    public static String URL() {
        return get("baseUrl");
    }
    public static String User() {
        return get("username");
    }
    public static String Pass() {
        return get("password");
    }
}

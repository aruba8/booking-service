package ca.erik.bs.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Erik Khalimov.
 */
public class PropertiesManager {

    public static Properties properties = new Properties();

    static {
        try {

            String CONFIG_FILE_NAME = "config.properties";
            properties.load(new FileInputStream(CONFIG_FILE_NAME));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

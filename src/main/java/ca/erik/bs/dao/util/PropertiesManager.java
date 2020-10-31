package ca.erik.bs.dao.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    public Properties properties = new Properties();

    public PropertiesManager() {
        String CONFIG_FILE_NAME = "config.properties";
        try {

            properties.load(new FileInputStream(CONFIG_FILE_NAME));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PropertiesManager(String propertiesFileName) {
        try {
            properties.load(new FileInputStream(propertiesFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

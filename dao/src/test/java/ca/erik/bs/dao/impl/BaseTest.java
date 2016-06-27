package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.util.PropertiesManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Erik Khalimov.
 */
public class BaseTest {
    @BeforeClass
    public static void setUpDb() throws Exception {
        PropertiesManager propertiesManager = new PropertiesManager("src/test/resources/testconfig.properties");
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(propertiesManager);
        Connection conn = daoFactory.getConnection();
        Statement statement = conn.createStatement();
        String sql = readFile("src/test/resources/createdb.sql");
        statement.execute(sql);
        conn.close();
    }

    @AfterClass
    public static void clearDb() throws Exception {
        PropertiesManager propertiesManager = new PropertiesManager("src/test/resources/testconfig.properties");
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(propertiesManager);
        Connection conn = daoFactory.getConnection();
        Statement statement = conn.createStatement();
        String sql = readFile("src/test/resources/dropdb.sql");
        statement.execute(sql);
        conn.close();
    }

    private static String readFile(String fileName) throws IOException {
        return FileUtils.readFileToString(new File(fileName));
    }

}

package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.util.PropertiesManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class BaseTest {
//    @ClassRule
//    public static GenericContainer psql = new GenericContainer(DockerImageName.parse("postgres:10.14")).withExposedPorts(5432);

    public static PostgresDaoFactory daoFactory;

    @BeforeClass
    public static void setUpDb() throws Exception {
        try (PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:10.14")) {
            psql.start();
            String postgresHost = psql.getHost();
            Integer postgresPort = psql.getFirstMappedPort();
            String userName = psql.getUsername();
            String password = psql.getPassword();
            String databaseName = psql.getDatabaseName();
            System.out.println(">>>"+postgresHost+">>>"+postgresPort+">>>"+userName+">>>"+password+">>>"+databaseName);
            daoFactory = new PostgresDaoFactory(postgresHost, postgresPort, userName, password, databaseName);
            Connection conn = daoFactory.getConnection();
            System.out.println("@@@ "+conn.isClosed());
            Statement statement = conn.createStatement();
            String sql = readFile("src/test/resources/createdb.sql");
            statement.execute(sql);
//            conn.close();
        }
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

    public static String readFile(String fileName) throws IOException {
        return FileUtils.readFileToString(new File(fileName));
    }

}

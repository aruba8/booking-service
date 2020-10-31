package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.model.Landlord;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class LandlordDaoImplTest {

    private static Connection connection;
    private static LandlordDao landlordDao;

    @ClassRule
    public static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:10.14");


    @BeforeClass
    public static void setUp() throws Exception {
        psql.start();
        String postgresHost = psql.getHost();
        Integer postgresPort = psql.getFirstMappedPort();
        String userName = psql.getUsername();
        String password = psql.getPassword();
        String databaseName = psql.getDatabaseName();
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(postgresHost, postgresPort, userName, password, databaseName);
        connection = daoFactory.getConnection();
        String sql = UtilHelper.readFile("src/test/resources/createdb.sql");
        Statement statement = connection.createStatement();
        statement.execute(sql);
        landlordDao = daoFactory.getLandlordDao(connection);
    }

    @After
    public void tearDown() throws Exception {
        landlordDao.deleteAll();
    }

    @AfterClass
    public static void close() throws Exception {
        connection.close();
    }

    @Test
    public void get() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setMiddleName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        Landlord savedLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        Landlord foundLandlord = landlordDao.get(savedLandlord.getId());
        Assert.assertEquals(savedLandlord.getEmail(), foundLandlord.getEmail());
        Assert.assertEquals(savedLandlord.getFirstName(), foundLandlord.getFirstName());
        Assert.assertEquals(savedLandlord.getLastName(), foundLandlord.getLastName());
        Assert.assertEquals(savedLandlord.getMiddleName(), foundLandlord.getMiddleName());
        Assert.assertEquals(savedLandlord.getPhoneNumber(), foundLandlord.getPhoneNumber());
    }

    @Test
    public void findLandlordByEmail() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setMiddleName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        Landlord savedLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        Assert.assertEquals(landlord.getEmail(), savedLandlord.getEmail());
        Assert.assertEquals(landlord.getFirstName(), savedLandlord.getFirstName());
        Assert.assertEquals(landlord.getMiddleName(), savedLandlord.getMiddleName());
        Assert.assertEquals(landlord.getLastName(), savedLandlord.getLastName());
        Assert.assertEquals(landlord.getPhoneNumber(), savedLandlord.getPhoneNumber());

    }

    @Test
    public void save() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setMiddleName("test");
        landlord.setLastName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        Landlord savedLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        Assert.assertEquals(landlord.getEmail(), savedLandlord.getEmail());
        Assert.assertEquals(landlord.getFirstName(), savedLandlord.getFirstName());
        Assert.assertEquals(landlord.getLastName(), savedLandlord.getLastName());
        Assert.assertEquals(landlord.getMiddleName(), savedLandlord.getMiddleName());
        Assert.assertEquals(landlord.getPhoneNumber(), savedLandlord.getPhoneNumber());
    }

    @Test
    public void update() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setPhoneNumber("123123123");
        landlord.setMiddleName("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        Landlord savedLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        savedLandlord.setPhoneNumber("345345345");
        landlordDao.update(savedLandlord);
        Landlord foundLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        Assert.assertEquals(savedLandlord.getEmail(), foundLandlord.getEmail());
        Assert.assertEquals(savedLandlord.getFirstName(), foundLandlord.getFirstName());
        Assert.assertEquals(savedLandlord.getLastName(), foundLandlord.getLastName());
        Assert.assertEquals(savedLandlord.getMiddleName(), foundLandlord.getMiddleName());
        Assert.assertEquals("345345345", foundLandlord.getPhoneNumber());

    }

    @Test
    public void delete() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setPhoneNumber("123123123");
        landlord.setMiddleName("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        Landlord savedLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        landlordDao.delete(savedLandlord);
        List<Landlord> foundLandlords = landlordDao.findAll();
        Assert.assertEquals(0, foundLandlords.size());

    }

}
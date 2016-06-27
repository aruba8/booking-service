package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.util.PropertiesManager;
import ca.erik.bs.model.Landlord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @author Erik Khalimov.
 */
public class LandlordDaoImplTest extends BaseTest {

    private Connection connection;
    private LandlordDao landlordDao;
    private Landlord testLandlord;


    @Before
    public void setUp() throws Exception {
        PropertiesManager propertiesManager = new PropertiesManager("src/test/resources/testconfig.properties");
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(propertiesManager);
        connection = daoFactory.getConnection();
        landlordDao = daoFactory.getLandlordDao(connection);
    }

    @After
    public void tearDown() throws Exception {
        landlordDao.deleteAll();
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
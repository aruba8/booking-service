package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.ApartmentDao;
import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.util.PropertiesManager;
import ca.erik.bs.model.Apartment;
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
public class ApartmentDaoImplTest extends BaseTest {


    private Connection connection;
    private ApartmentDao apartmentDao;
    private LandlordDao landlordDao;
    private Landlord testLandlord;


    @Before
    public void setUp() throws Exception {
        PropertiesManager propertiesManager = new PropertiesManager("src/test/resources/testconfig.properties");
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(propertiesManager);
        connection = daoFactory.getConnection();
        apartmentDao = daoFactory.getApartmentDao(connection);
        landlordDao = daoFactory.getLandlordDao(connection);
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setMiddleName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        testLandlord = landlordDao.findLandlordByEmail("test@test.ca");
    }

    @After
    public void tearDown() throws Exception {
        apartmentDao.deleteAll();
        landlordDao.delete(testLandlord);
        connection.close();
    }

    @Test
    public void save() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setAddress("123 main str");
        apartment.setPrice(234d);
        apartment.setLandlordId(testLandlord.getId());
        apartmentDao.save(apartment);
        List<Apartment> apartments = apartmentDao.findByLandlordId(testLandlord.getId());
        Assert.assertEquals(1, apartments.size());
        Assert.assertEquals("123 main str", apartments.get(0).getAddress());
        Assert.assertEquals(234d, apartments.get(0).getPrice(), 0);

    }

    @Test
    public void get() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setAddress("444 main str");
        apartment.setPrice(222d);
        apartment.setLandlordId(testLandlord.getId());
        apartmentDao.save(apartment);
        List<Apartment> apartments = apartmentDao.findByLandlordId(testLandlord.getId());
        Apartment savedApartment = apartments.get(0);
        Apartment foundApartment = apartmentDao.get(savedApartment.getId());
        Assert.assertEquals("444 main str", foundApartment.getAddress());
        Assert.assertEquals(222d, foundApartment.getPrice(), 0);

    }

    @Test
    public void update() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setAddress("444 main str");
        apartment.setPrice(222d);
        apartment.setLandlordId(testLandlord.getId());
        apartmentDao.save(apartment);
        List<Apartment> apartments = apartmentDao.findByLandlordId(testLandlord.getId());
        Apartment savedApartment = apartments.get(0);
        savedApartment.setAddress("555 Erin Str");
        apartmentDao.update(savedApartment);
        Apartment foundApartment = apartmentDao.get(savedApartment.getId());
        Assert.assertEquals("555 Erin Str", foundApartment.getAddress());
        Assert.assertEquals(222d, foundApartment.getPrice(), 0);

    }

    @Test
    public void delete() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setAddress("444 main str");
        apartment.setPrice(222d);
        apartment.setLandlordId(testLandlord.getId());
        apartmentDao.save(apartment);
        List<Apartment> apartments = apartmentDao.findByLandlordId(testLandlord.getId());
        Apartment savedApartment = apartments.get(0);
        apartmentDao.delete(savedApartment);
        List<Apartment> foundApartments = apartmentDao.findByLandlordId(savedApartment.getId());
        Assert.assertEquals(0, foundApartments.size());

    }

    @Test
    public void getAll() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setAddress("444 main str");
        apartment.setPrice(222d);
        apartment.setLandlordId(testLandlord.getId());
        Apartment apartment2 = new Apartment();
        apartment2.setAddress("111 main str");
        apartment2.setPrice(222d);
        apartment2.setLandlordId(testLandlord.getId());
        apartmentDao.save(apartment);
        apartmentDao.save(apartment2);
        List<Apartment> apartments = apartmentDao.getAll();
        Assert.assertEquals(2, apartments.size());
    }


}
package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.ApartmentDao;
import ca.erik.bs.dao.BookingPeriodDao;
import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.dao.util.PropertiesManager;
import ca.erik.bs.model.Apartment;
import ca.erik.bs.model.BookingPeriod;
import ca.erik.bs.model.Landlord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class BookingPeriodDaoImplTest extends BaseTest {

    private Connection connection;
    private ApartmentDao apartmentDao;
    private BookingPeriodDao bookingPeriodDao;
    private LandlordDao landlordDao;
    private Apartment apartmentTest;
    private Landlord landlordTest;

    @Before
    public void setUp() throws Exception {
        PropertiesManager propertiesManager = new PropertiesManager("src/test/resources/testconfig.properties");
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(propertiesManager);
        connection = daoFactory.getConnection();
        apartmentDao = daoFactory.getApartmentDao(connection);
        bookingPeriodDao = daoFactory.getBookingPeriodDao(connection);
        landlordDao = daoFactory.getLandlordDao(connection);
        landlordTest = createAndReturnLandlord();
        apartmentTest = createAndReturnApartment(landlordTest);
    }

    @After
    public void tearDown() throws Exception {
        bookingPeriodDao.deleteAll();
        apartmentDao.deleteAll();
        landlordDao.deleteAll();
        connection.close();
    }

    @Test
    public void save() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        BookingPeriod bookingPeriod = new BookingPeriod();
        bookingPeriod.setApartment_id(apartmentTest.getId());
        bookingPeriod.setFromDate(new Date(sdf.parse("22/06/2016").getTime()));
        bookingPeriod.setToDate(new Date(sdf.parse("24/06/2016").getTime()));

        bookingPeriodDao.save(bookingPeriod);

        BookingPeriod savedPeriod = bookingPeriodDao.findByApartmentId(apartmentTest.getId()).get(0);
        Assert.assertEquals(bookingPeriod.getFromDate(), savedPeriod.getFromDate());
        Assert.assertEquals(bookingPeriod.getApartment_id(), savedPeriod.getApartment_id());
        Assert.assertEquals(bookingPeriod.getToDate(), savedPeriod.getToDate());

    }

    @Test
    public void update() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        BookingPeriod bookingPeriod = new BookingPeriod();
        bookingPeriod.setFromDate(new Date(sdf.parse("22/06/2016").getTime()));
        bookingPeriod.setApartment_id(apartmentTest.getId());
        bookingPeriod.setToDate(new Date(sdf.parse("24/06/2016").getTime()));
        bookingPeriodDao.save(bookingPeriod);
        BookingPeriod savedPeriod = bookingPeriodDao.findByApartmentId(apartmentTest.getId()).get(0);
        savedPeriod.setToDate(new Date(sdf.parse("24/08/2016").getTime()));

        bookingPeriodDao.update(savedPeriod);

        BookingPeriod foundPeriod = bookingPeriodDao.findByApartmentId(apartmentTest.getId()).get(0);
        Assert.assertEquals(bookingPeriod.getApartment_id(), foundPeriod.getApartment_id());
        Assert.assertEquals(bookingPeriod.getFromDate(), foundPeriod.getFromDate());
        Assert.assertEquals(savedPeriod.getToDate(), foundPeriod.getToDate());

    }

    private Landlord createAndReturnLandlord() throws DatabaseException {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setMiddleName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        return landlordDao.findLandlordByEmail("test@test.ca");
    }

    private Apartment createAndReturnApartment(Landlord landlord) throws DatabaseException {
        Apartment apartment = new Apartment();
        apartment.setAddress("123 main str");
        apartment.setPrice(234d);
        apartment.setLandlordId(landlord.getId());
        apartmentDao.save(apartment);
        return apartmentDao.findByLandlordId(landlord.getId()).get(0);
    }

}
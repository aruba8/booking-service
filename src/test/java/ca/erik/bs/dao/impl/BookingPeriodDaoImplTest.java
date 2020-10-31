package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.ApartmentDao;
import ca.erik.bs.dao.BookingPeriodDao;
import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Apartment;
import ca.erik.bs.model.BookingPeriod;
import ca.erik.bs.model.Landlord;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class BookingPeriodDaoImplTest {

    private static Connection connection;
    private static ApartmentDao apartmentDao;
    private static BookingPeriodDao bookingPeriodDao;
    private static LandlordDao landlordDao;
    private static Apartment apartmentTest;
    private static Landlord testLandlord;

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
        apartmentDao = daoFactory.getApartmentDao(connection);
        bookingPeriodDao = daoFactory.getBookingPeriodDao(connection);
        landlordDao = daoFactory.getLandlordDao(connection);

    }

    @AfterClass
    public static void close() throws Exception{
        connection.close();
    }

    @Before
    public void createData() throws Exception{
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setMiddleName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        testLandlord = landlordDao.findLandlordByEmail("test@test.ca");
        testLandlord = createAndReturnLandlord();
        apartmentTest = createAndReturnApartment(testLandlord);
    }

    @After
    public void tearDown() throws Exception {
        bookingPeriodDao.deleteAll();
        apartmentDao.deleteAll();
        landlordDao.deleteAll();
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

    private static Landlord createAndReturnLandlord() throws DatabaseException {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setLastName("test");
        landlord.setMiddleName("test");
        landlord.setPhoneNumber("test");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        return landlordDao.findLandlordByEmail("test@test.ca");
    }

    private static Apartment createAndReturnApartment(Landlord landlord) throws DatabaseException {
        Apartment apartment = new Apartment();
        apartment.setAddress("123 main str");
        apartment.setPrice(234d);
        apartment.setLandlordId(landlord.getId());
        apartmentDao.save(apartment);
        return apartmentDao.findByLandlordId(landlord.getId()).get(0);
    }

}
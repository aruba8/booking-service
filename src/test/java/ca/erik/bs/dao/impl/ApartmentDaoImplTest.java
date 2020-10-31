package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.ApartmentDao;
import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.model.Apartment;
import ca.erik.bs.model.Landlord;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;


public class ApartmentDaoImplTest {

    private static Connection connection;
    private static ApartmentDao apartmentDao;
    private static LandlordDao landlordDao;
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
        landlordDao = daoFactory.getLandlordDao(connection);
    }

    @Before
    public void createDb() throws Exception {
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
    public void dropDb() throws Exception {
        apartmentDao.deleteAll();
        landlordDao.delete(testLandlord);
    }

    @AfterClass
    public static void tearDown() throws Exception {
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
        try {
            apartmentDao.save(apartment);
            apartmentDao.save(apartment2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Apartment> apartments = apartmentDao.getAll();
        Assert.assertEquals(2, apartments.size());
    }


}
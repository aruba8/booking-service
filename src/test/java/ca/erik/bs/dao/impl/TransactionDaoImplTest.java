package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.*;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.*;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransactionDaoImplTest {

    private static Connection connection;
    private static ApartmentDao apartmentDao;
    private static BookingPeriodDao bookingPeriodDao;
    private static LandlordDao landlordDao;
    private static TenantDao tenantDao;
    private static TransactionDao transactionDao;
    private static Tenant tenantTest;
    private static Apartment apartmentTest;
    private static BookingPeriod bookingPeriodTest;

    @ClassRule
    public static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:10.14");

    @BeforeClass
    public static void setUp() throws Exception {
        psql.start();
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(psql.getHost(), psql.getFirstMappedPort(),
                psql.getUsername(), psql.getPassword(), psql.getDatabaseName());
        connection = daoFactory.getConnection();
        String sql = BaseTest.readFile("src/test/resources/createdb.sql");
        Statement statement = connection.createStatement();
        statement.execute(sql);

        apartmentDao = daoFactory.getApartmentDao(connection);
        bookingPeriodDao = daoFactory.getBookingPeriodDao(connection);
        landlordDao = daoFactory.getLandlordDao(connection);
        tenantDao = daoFactory.getTenantDao(connection);
        transactionDao = daoFactory.getTransactionDao(connection);

    }

    @AfterClass
    public static void close() throws Exception {
        connection.close();
    }

    @Before
    public void createData() throws Exception {
        tenantTest = createAndReturnTenant();
        Landlord landlordTest = createAndReturnLandlord();
        apartmentTest = createAndReturnApartment(landlordTest);
        bookingPeriodTest = createAndReturnBookingPeriod(apartmentTest);
    }

    @After
    public void tearDown() throws Exception {
        transactionDao.deleteAll();
        bookingPeriodDao.deleteAll();
        apartmentDao.deleteAll();
        landlordDao.deleteAll();
        tenantDao.deleteAll();
    }

    private static BookingPeriod createAndReturnBookingPeriod(Apartment apartment) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        BookingPeriod bookingPeriod = new BookingPeriod();
        bookingPeriod.setApartment_id(apartment.getId());
        bookingPeriod.setFromDate(new Date(sdf.parse("22/06/2016").getTime()));
        bookingPeriod.setToDate(new Date(sdf.parse("24/06/2016").getTime()));
        bookingPeriodDao.save(bookingPeriod);
        return bookingPeriodDao.findByApartmentId(apartment.getId()).get(0);
    }

    private static Apartment createAndReturnApartment(Landlord landlord) throws DatabaseException {
        Apartment apartment = new Apartment();
        apartment.setPrice(234d);
        apartment.setAddress("123 main str");
        apartment.setLandlordId(landlord.getId());
        apartmentDao.save(apartment);
        return apartmentDao.findByLandlordId(landlord.getId()).get(0);
    }

    private static Landlord createAndReturnLandlord() throws DatabaseException {
        Landlord landlord = new Landlord();
        landlord.setFirstName("test");
        landlord.setMiddleName("test");
        landlord.setLastName("test");
        landlord.setPhoneNumber("555567");
        landlord.setEmail("test@test.ca");
        landlordDao.save(landlord);
        return landlordDao.findLandlordByEmail("test@test.ca");
    }

    private static Tenant createAndReturnTenant() throws DatabaseException {
        Tenant tenant = new Tenant();
        tenant.setMiddleName("test");
        tenant.setFirstName("test");
        tenant.setLastName("test");
        tenant.setPhoneNumber("3475437543");
        tenant.setEmail("test@test.ca");
        tenantDao.save(tenant);
        return tenantDao.findByEmail("test@test.ca");
    }

    @Test
    public void save() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setApartmentId(apartmentTest.getId());
        transaction.setBookingPeriodId(bookingPeriodTest.getId());
        transaction.setSum(44d);
        transaction.setTenantId(tenantTest.getId());
        transaction.setTransactionTime(new Date(new java.util.Date().getTime()));

        transactionDao.save(transaction);

        List<Transaction> transactions = transactionDao.findByTenantId(tenantTest.getId());
        Transaction foundTransaction = transactions.get(0);
        Assert.assertEquals(transaction.getApartmentId(), foundTransaction.getApartmentId());
        Assert.assertEquals(transaction.getBookingPeriodId(), foundTransaction.getBookingPeriodId());
        Assert.assertEquals(transaction.getSum(), foundTransaction.getSum(), 0);
        Assert.assertEquals(transaction.getTenantId(), foundTransaction.getTenantId());

    }

    @Test
    public void get() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setApartmentId(apartmentTest.getId());
        transaction.setBookingPeriodId(bookingPeriodTest.getId());
        transaction.setSum(44d);
        transaction.setTenantId(tenantTest.getId());
        transaction.setTransactionTime(new Date(new java.util.Date().getTime()));
        transactionDao.save(transaction);
        List<Transaction> transactions = transactionDao.findByTenantId(tenantTest.getId());
        Transaction savedTransaction = transactions.get(0);
        Transaction foundTransaction = transactionDao.get(savedTransaction.getId());

        Assert.assertEquals(savedTransaction.getApartmentId(), foundTransaction.getApartmentId());
        Assert.assertEquals(savedTransaction.getBookingPeriodId(), foundTransaction.getBookingPeriodId());
        Assert.assertEquals(savedTransaction.getSum(), foundTransaction.getSum(), 0);
        Assert.assertEquals(savedTransaction.getTenantId(), foundTransaction.getTenantId());

    }

    @Test
    public void update() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setApartmentId(apartmentTest.getId());
        transaction.setBookingPeriodId(bookingPeriodTest.getId());
        transaction.setTenantId(tenantTest.getId());
        transaction.setSum(44d);
        transaction.setTransactionTime(new Date(new java.util.Date().getTime()));
        transactionDao.save(transaction);
        List<Transaction> transactions = transactionDao.findByTenantId(tenantTest.getId());
        Transaction savedTransaction = transactions.get(0);
        savedTransaction.setSum(567d);

        transactionDao.update(savedTransaction);
        Transaction foundTransaction = transactionDao.get(savedTransaction.getId());

        Assert.assertEquals(savedTransaction.getApartmentId(), foundTransaction.getApartmentId());
        Assert.assertEquals(savedTransaction.getBookingPeriodId(), foundTransaction.getBookingPeriodId());
        Assert.assertEquals(savedTransaction.getSum(), foundTransaction.getSum(), 0);
        Assert.assertEquals(savedTransaction.getTenantId(), foundTransaction.getTenantId());

    }

    @Test
    public void delete() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setApartmentId(apartmentTest.getId());
        transaction.setBookingPeriodId(bookingPeriodTest.getId());
        transaction.setTenantId(tenantTest.getId());
        transaction.setSum(44d);
        transaction.setTransactionTime(new Date(new java.util.Date().getTime()));
        transactionDao.save(transaction);
        List<Transaction> transactions = transactionDao.findByTenantId(tenantTest.getId());
        Transaction savedTransaction = transactions.get(0);

        transactionDao.delete(savedTransaction);

        List<Transaction> foundTransactions = transactionDao.findByTenantId(tenantTest.getId());

        Assert.assertEquals(0, foundTransactions.size());

    }

}
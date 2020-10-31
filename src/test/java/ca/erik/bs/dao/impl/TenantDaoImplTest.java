package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.PostgresDaoFactory;
import ca.erik.bs.dao.TenantDao;
import ca.erik.bs.model.Tenant;
import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.Statement;

public class TenantDaoImplTest {

    private static Connection connection;
    private static TenantDao tenantDao;

    @ClassRule
    public static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:10.14");

    @BeforeClass
    public static void setUp() throws Exception {
        psql.start();
        PostgresDaoFactory daoFactory = new PostgresDaoFactory(psql.getHost(), psql.getFirstMappedPort(),
                psql.getUsername(), psql.getPassword(), psql.getDatabaseName());
        connection = daoFactory.getConnection();
        String sql = UtilHelper.readFile("src/test/resources/createdb.sql");
        Statement statement = connection.createStatement();
        statement.execute(sql);
        tenantDao = daoFactory.getTenantDao(connection);
    }

    @After
    public void tearDown() throws Exception {
        tenantDao.deleteAll();
    }

    @AfterClass
    public static void close() throws Exception {
        connection.close();
    }

    @Test
    public void get() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setFirstName("test");
        tenant.setLastName("test");
        tenant.setMiddleName("test");
        tenant.setPhoneNumber("test");
        tenant.setEmail("test@test.ca");
        tenantDao.save(tenant);
        Tenant savedTenant = tenantDao.findByEmail("test@test.ca");

        Tenant foundTenant = tenantDao.get(savedTenant.getId());

        Assert.assertEquals(tenant.getEmail(), foundTenant.getEmail());
        Assert.assertEquals(tenant.getFirstName(), foundTenant.getFirstName());
        Assert.assertEquals(tenant.getMiddleName(), foundTenant.getMiddleName());
        Assert.assertEquals(tenant.getLastName(), foundTenant.getLastName());
        Assert.assertEquals(tenant.getPhoneNumber(), foundTenant.getPhoneNumber());

    }

    @Test
    public void save() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setFirstName("test");
        tenant.setMiddleName("test");
        tenant.setLastName("test");
        tenant.setPhoneNumber("3475437543");
        tenant.setEmail("test@test.ca");

        tenantDao.save(tenant);

        Tenant savedTenant = tenantDao.findByEmail("test@test.ca");
        Assert.assertEquals(tenant.getEmail(), savedTenant.getEmail());
        Assert.assertEquals(tenant.getFirstName(), savedTenant.getFirstName());
        Assert.assertEquals(tenant.getLastName(), savedTenant.getLastName());
        Assert.assertEquals(tenant.getMiddleName(), savedTenant.getMiddleName());
        Assert.assertEquals(tenant.getPhoneNumber(), savedTenant.getPhoneNumber());
    }

    @Test
    public void update() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setFirstName("test");
        tenant.setMiddleName("test");
        tenant.setLastName("test");
        tenant.setPhoneNumber("3475437543");
        tenant.setEmail("test@test.ca");
        tenantDao.save(tenant);
        Tenant savedTenant = tenantDao.findByEmail("test@test.ca");
        savedTenant.setEmail("new@email.ca");

        tenantDao.update(savedTenant);

        Tenant foundTenant = tenantDao.get(savedTenant.getId());
        Assert.assertEquals("new@email.ca", foundTenant.getEmail());
    }

    @Test
    public void delete() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setMiddleName("test");
        tenant.setFirstName("test");
        tenant.setLastName("test");
        tenant.setPhoneNumber("3475437543");
        tenant.setEmail("test@test.ca");
        tenantDao.save(tenant);
        Tenant savedTenant = tenantDao.findByEmail("test@test.ca");

        tenantDao.delete(savedTenant);

        Tenant foundTenant = tenantDao.findByEmail("test@test.ca");
        Assert.assertNull(foundTenant);
    }
}

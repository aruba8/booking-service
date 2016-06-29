package ca.erik.bs.dao;

import ca.erik.bs.dao.impl.*;
import ca.erik.bs.dao.util.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDaoFactory implements DaoFactory {

    private PropertiesManager propertiesManager;

    private String user;
    private String password;
    private String url;
    private String driver;

    public PostgresDaoFactory() {
        this.propertiesManager = new PropertiesManager();
        try {
            initProperties();
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PostgresDaoFactory(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
        try {
            initProperties();
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initProperties() {
        this.user = propertiesManager.properties.getProperty("db.user");
        this.password = propertiesManager.properties.getProperty("db.password");
        this.url = propertiesManager.properties.getProperty("db.url");
        this.driver = propertiesManager.properties.getProperty("db.driver");
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public ApartmentDao getApartmentDao(Connection connection) {
        return new ApartmentDaoImpl(connection);
    }

    public BookingPeriodDao getBookingPeriodDao(Connection connection) {
        return new BookingPeriodDaoImpl(connection);
    }

    public LandlordDao getLandlordDao(Connection connection) {
        return new LandlordDaoImpl(connection);
    }

    public TenantDao getTenantDao(Connection connection) {
        return new TenantDaoImpl(connection);
    }

    public TransactionDao getTransactionDao(Connection connection) {
        return new TransactionDaoImpl(connection);
    }
}

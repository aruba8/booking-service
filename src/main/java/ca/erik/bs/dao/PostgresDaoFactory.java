package ca.erik.bs.dao;

import ca.erik.bs.dao.impl.*;
import ca.erik.bs.utils.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Erik Khalimov.
 */
public class PostgresDaoFactory implements DaoFactory {

    PropertiesManager propertiesManager = new PropertiesManager();
    private String user = PropertiesManager.properties.getProperty("db.user");
    private String password = PropertiesManager.properties.getProperty("db.password");;
    private String url = PropertiesManager.properties.getProperty("db.url");
    private String driver = PropertiesManager.properties.getProperty("db.driver");

    public PostgresDaoFactory(){
        try{
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

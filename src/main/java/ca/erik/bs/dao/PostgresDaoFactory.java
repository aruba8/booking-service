package ca.erik.bs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Erik Khalimov.
 */
public class PostgresDaoFactory implements DaoFactory {

    private String user = "bs";
    private String password = "bs";
    private String url = "jdbc:postgresql://localhost:5432/bs";
    private String driver = "org.postgresql.Driver";

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

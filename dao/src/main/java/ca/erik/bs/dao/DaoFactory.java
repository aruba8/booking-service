package ca.erik.bs.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Erik Khalimov.
 */
public interface DaoFactory {
    Connection getConnection() throws SQLException;

    ApartmentDao getApartmentDao(Connection connection);

    BookingPeriodDao getBookingPeriodDao(Connection connection);

    LandlordDao getLandlordDao(Connection connection);

    TenantDao getTenantDao(Connection connection);

    TransactionDao getTransactionDao(Connection connection);
}

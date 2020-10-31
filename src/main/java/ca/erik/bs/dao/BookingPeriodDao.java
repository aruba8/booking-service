package ca.erik.bs.dao;

import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.BookingPeriod;

import java.util.List;

public interface BookingPeriodDao {

    void save(BookingPeriod bookingPeriod) throws DatabaseException;

    void update(BookingPeriod bookingPeriod) throws DatabaseException;

    List<BookingPeriod> findByApartmentId(int apartmentId) throws DatabaseException;

    void deleteAll() throws DatabaseException;
}

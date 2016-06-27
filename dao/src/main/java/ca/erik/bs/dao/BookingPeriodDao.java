package ca.erik.bs.dao;

import ca.erik.bs.model.BookingPeriod;

import java.util.List;

/**
 * @author Erik Khalimov.
 */
public interface BookingPeriodDao {

    void save(BookingPeriod bookingPeriod);

    void update(BookingPeriod bookingPeriod);

    List<BookingPeriod> findByApartmentId(int apartmentId);

    void deleteAll();
}

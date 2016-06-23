package ca.erik.bs.dao;

import ca.erik.bs.model.BookingPeriod;

/**
 * @author Erik Khalimov.
 */
public interface BookingPeriodDao {

    void save(BookingPeriod bookingPeriod);

    void update(BookingPeriod bookingPeriod);
}

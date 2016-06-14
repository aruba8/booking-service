package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.BookingPeriodDao;
import ca.erik.bs.model.BookingPeriod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Erik Khalimov.
 */
public class BookingPeriodDaoImpl implements BookingPeriodDao {

    private final Connection connection;

    public BookingPeriodDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(BookingPeriod bookingPeriod) {
        String sql = "INSERT INTO booking_period (from_date, to_date, apartment_id) VALUES (?,?,?);";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setDate(1, bookingPeriod.getFromDate());
            pstm.setDate(2, bookingPeriod.getToDate());
            pstm.setInt(3, bookingPeriod.getApartment_id());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(BookingPeriod bookingPeriod) {
        String sql = "UPDATE booking_period SET from_date=?, to_date=?, apartment_id=? WHERE id=?;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setDate(1, bookingPeriod.getFromDate());
            pstm.setDate(2, bookingPeriod.getToDate());
            pstm.setInt(3, bookingPeriod.getApartment_id());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

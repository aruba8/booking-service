package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.BookingPeriodDao;
import ca.erik.bs.model.BookingPeriod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            pstm.setInt(3, bookingPeriod.getApartment_id());
            pstm.setDate(2, bookingPeriod.getToDate());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(BookingPeriod bookingPeriod) {
        String sql = "UPDATE booking_period SET from_date=?, to_date=?, apartment_id=? WHERE id=?;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setDate(2, bookingPeriod.getToDate());
            pstm.setDate(1, bookingPeriod.getFromDate());
            pstm.setInt(3, bookingPeriod.getApartment_id());
            pstm.setInt(4, bookingPeriod.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BookingPeriod> findByApartmentId(int apartmentId) {
        String sql = "SELECT * FROM booking_period WHERE apartment_id=?;";
        List<BookingPeriod> bookingPeriodList = new ArrayList<BookingPeriod>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, apartmentId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                BookingPeriod bookingPeriod = new BookingPeriod();
                bookingPeriod.setApartment_id(rs.getInt("apartment_id"));
                bookingPeriod.setId(rs.getInt("id"));
                bookingPeriod.setFromDate(rs.getDate("from_date"));
                bookingPeriod.setToDate(rs.getDate("to_date"));
                bookingPeriodList.add(bookingPeriod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingPeriodList;

    }

    public void deleteAll() {
        String sql = "DELETE FROM booking_period;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

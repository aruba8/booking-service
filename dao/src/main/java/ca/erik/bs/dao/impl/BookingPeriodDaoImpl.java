package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.BookingPeriodDao;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.BookingPeriod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingPeriodDaoImpl extends BaseDao implements BookingPeriodDao {

    private final String UPDATE_QUERY = "UPDATE booking_period SET from_date=?, to_date=?, apartment_id=? WHERE id=?;";

    private final String FIND_BY_APARTMENT_ID_QUERY = "SELECT * FROM booking_period WHERE apartment_id=?;";

    private final String DELETE_ALL_QUERY = "DELETE FROM booking_period;";

    private final String SAVE_QUERY = "INSERT INTO booking_period (from_date, to_date, apartment_id) VALUES (?,?,?);";

    public BookingPeriodDaoImpl(Connection connection) {
        super(connection);
    }

    public void save(BookingPeriod bookingPeriod) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(SAVE_QUERY);
            pstm.setDate(1, bookingPeriod.getFromDate());
            pstm.setInt(3, bookingPeriod.getApartment_id());
            pstm.setDate(2, bookingPeriod.getToDate());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void update(BookingPeriod bookingPeriod) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(UPDATE_QUERY);
            pstm.setDate(2, bookingPeriod.getToDate());
            pstm.setDate(1, bookingPeriod.getFromDate());
            pstm.setInt(3, bookingPeriod.getApartment_id());
            pstm.setInt(4, bookingPeriod.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public List<BookingPeriod> findByApartmentId(int apartmentId) throws DatabaseException {
        List<BookingPeriod> bookingPeriodList = new ArrayList<BookingPeriod>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(FIND_BY_APARTMENT_ID_QUERY);
            pstm.setInt(1, apartmentId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                BookingPeriod bookingPeriod = new BookingPeriod();
                bookingPeriod.setApartment_id(rs.getInt("apartment_id"));
                bookingPeriod.setId(rs.getInt("id"));
                bookingPeriod.setFromDate(rs.getDate("from_date"));
                bookingPeriod.setToDate(rs.getDate("to_date"));
                bookingPeriodList.add(bookingPeriod);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return bookingPeriodList;

    }

    public void deleteAll() throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(DELETE_ALL_QUERY);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }
}

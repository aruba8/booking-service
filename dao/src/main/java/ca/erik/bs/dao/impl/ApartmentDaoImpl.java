package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.ApartmentDao;
import ca.erik.bs.model.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik Khalimov.
 */
public class ApartmentDaoImpl implements ApartmentDao {

    private final Connection connection;

    public ApartmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(Apartment apartment) {
        String sql = "INSERT INTO apartment (address, price, landlord_id)  VALUES (?, ?, ?);";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, apartment.getAddress());
            pstm.setDouble(2, apartment.getPrice());
            pstm.setInt(3, apartment.getLandlordId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Apartment get(int key) {
        String sql = "SELECT * FROM apartment WHERE id=?;";
        Apartment apartment = new Apartment();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, key);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                apartment.setId(rs.getInt("id"));
                apartment.setAddress(rs.getString("address"));
                apartment.setPrice(rs.getDouble("price"));
                apartment.setLandlordId(rs.getInt("id"));
                apartment.setId(rs.getInt("landlord_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartment;
    }

    public List<Apartment> findByLandlordId(int landlordId) {
        String sql = "SELECT * FROM apartment WHERE landlord_id=?;";
        List<Apartment> apartmentList = new ArrayList<Apartment>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, landlordId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Apartment apartment = new Apartment();
                apartment.setAddress(rs.getString("address"));
                apartment.setId(rs.getInt("id"));
                apartment.setPrice(rs.getDouble("price"));
                apartment.setLandlordId(rs.getInt("landlord_id"));
                apartmentList.add(apartment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartmentList;
    }

    public void update(Apartment apartment) {
        String sql = "UPDATE apartment SET address=?, price=?, landlord_id=? WHERE id=?;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, apartment.getAddress());
            pstm.setDouble(2, apartment.getPrice());
            pstm.setInt(3, apartment.getLandlordId());
            pstm.setInt(4, apartment.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Apartment apartment) {
        String sql = "DELETE FROM apartment WHERE id=?;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, apartment.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM apartment;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Apartment> getAll() {
        String sql = "SELECT * FROM apartment;";
        List<Apartment> apartmentList = new ArrayList<Apartment>();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(rs.getInt("id"));
                apartment.setAddress(rs.getString("address"));
                apartment.setLandlordId(rs.getInt("landlord_id"));
                apartment.setPrice(rs.getDouble("price"));
                apartmentList.add(apartment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartmentList;

    }
}

package ca.erik.bs.dao;

import ca.erik.bs.model.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            rs.next();
            apartment.setId(rs.getInt("id"));
            apartment.setAddress(rs.getString("address"));
            apartment.setPrice(rs.getDouble("price"));
            apartment.setLandlordId(rs.getInt("id"));
            apartment.setId(rs.getInt("landlord_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartment;
    }

    public void update(Apartment apartment) {
        String sql = "UPDATE apartment SET address=?, price=?, landlord_id=? WHERE id=?;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, apartment.getAddress());
            pstm.setDouble(2, apartment.getPrice());
            pstm.setInt(3, apartment.getLandlordId());
            pstm.setInt(4, apartment.getId());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Apartment apartment) {

    }

    public List<Apartment> getAll() {
        return null;
    }
}

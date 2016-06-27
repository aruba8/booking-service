package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.model.Landlord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik Khalimov.
 */
public class LandlordDaoImpl implements LandlordDao {

    private final Connection connection;

    public LandlordDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Landlord get(int key) {
        String sql = "SELECT * FROM landlord WHERE id = ?;";
        PreparedStatement pstm;
        Landlord landlord = new Landlord();
        try {
            pstm = this.connection.prepareStatement(sql);
            pstm.setInt(1, key);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            landlord.setId(rs.getInt("id"));
            landlord.setFirstName(rs.getString("first_name"));
            landlord.setLastName(rs.getString("last_name"));
            landlord.setMiddleName(rs.getString("middle_name"));
            landlord.setPhoneNumber(rs.getString("phone_number"));
            landlord.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return landlord;
    }

    public Landlord findLandlordByEmail(String email) {
        String sql = "SELECT * FROM landlord WHERE email=?;";
        PreparedStatement pstm;
        Landlord landlord = new Landlord();
        try {
            pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            landlord.setId(rs.getInt("id"));
            landlord.setFirstName(rs.getString("first_name"));
            landlord.setLastName(rs.getString("last_name"));
            landlord.setMiddleName(rs.getString("middle_name"));
            landlord.setPhoneNumber(rs.getString("phone_number"));
            landlord.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return landlord;
    }

    public void save(Landlord landLord) {
        String sql = "INSERT INTO landlord (first_name, middle_name, last_name, phone_number, email) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, landLord.getFirstName());
            pstm.setString(2, landLord.getMiddleName());
            pstm.setString(3, landLord.getLastName());
            pstm.setString(4, landLord.getPhoneNumber());
            pstm.setString(5, landLord.getEmail());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Landlord landLord) {
        String sql = "UPDATE landlord SET first_name=?, middle_name=?, last_name=?, phone_number=?, email=? WHERE id=?;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, landLord.getFirstName());
            pstm.setString(2, landLord.getMiddleName());
            pstm.setString(3, landLord.getLastName());
            pstm.setString(4, landLord.getPhoneNumber());
            pstm.setString(5, landLord.getEmail());
            pstm.setInt(6, landLord.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Landlord landLord) {
        String sql = "DELETE FROM landlord WHERE id=?;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setInt(1, landLord.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM landlord;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Landlord> findAll() {
        String sql = "SELECT * FROM landlord;";
        PreparedStatement pstm;
        List<Landlord> landlordList = new ArrayList<Landlord>();
        try {
            pstm = this.connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Landlord landlord = new Landlord();
                landlord.setId(rs.getInt("id"));
                landlord.setFirstName(rs.getString("first_name"));
                landlord.setLastName(rs.getString("last_name"));
                landlord.setMiddleName(rs.getString("middle_name"));
                landlord.setPhoneNumber(rs.getString("phone_number"));
                landlord.setEmail(rs.getString("email"));
                landlordList.add(landlord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return landlordList;
    }

}

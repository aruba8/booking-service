package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.LandlordDao;
import ca.erik.bs.model.Landlord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void save(Landlord landLord){
        String sql = "INSERT INTO landlord (first_name, middle_name, last_name, phone_number, email) VALUES (?, ?, ?, ?, ?);";
        try {
        PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, landLord.getFirstName());
            pstm.setString(2, landLord.getMiddleName());
            pstm.setString(3, landLord.getLastName());
            pstm.setString(4, landLord.getPhoneNumber());
            pstm.setString(5, landLord.getEmail());
            pstm.execute();
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
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Landlord landLord) {

    }

}

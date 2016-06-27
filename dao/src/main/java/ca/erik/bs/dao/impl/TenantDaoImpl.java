package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.TenantDao;
import ca.erik.bs.model.Tenant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Erik Khalimov.
 */
public class TenantDaoImpl implements TenantDao {

    private final Connection connection;

    public TenantDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Tenant get(int key) {
        String sql = "SELECT * FROM tenant WHERE id = ?;";
        Tenant tenant = new Tenant();
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setInt(1, key);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            tenant.setId(rs.getInt("id"));
            tenant.setFirstName(rs.getString("first_name"));
            tenant.setLastName(rs.getString("last_name"));
            tenant.setMiddleName(rs.getString("middle_name"));
            tenant.setPhoneNumber(rs.getString("phone_number"));
            tenant.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }

    public void save(Tenant tenant) {
        String sql = "INSERT INTO tenant (first_name, last_name, middle_name, phone_number, email) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pstm;
        try {
            pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, tenant.getFirstName());
            pstm.setString(2, tenant.getLastName());
            pstm.setString(3, tenant.getMiddleName());
            pstm.setString(4, tenant.getPhoneNumber());
            pstm.setString(5, tenant.getEmail());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Tenant tenant) {
        String sql = "UPDATE tenant SET first_name=?, middle_name=?, last_name=?, phone_number=?, email=? WHERE id=?;";
        PreparedStatement pstm;
        try {
            pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, tenant.getFirstName());
            pstm.setString(2, tenant.getMiddleName());
            pstm.setString(3, tenant.getLastName());
            pstm.setString(4, tenant.getPhoneNumber());
            pstm.setString(5, tenant.getEmail());
            pstm.setInt(6, tenant.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Tenant tenant) {
        String sql = "DELETE FROM tenant WHERE id=?;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, tenant.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deleteAll() {
        String sql = "DELETE FROM tenant;";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Tenant findByEmail(String email) {
        String sql = "SELECT * FROM tenant WHERE email = ?;";
        Tenant tenant = new Tenant();
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            tenant.setId(rs.getInt("id"));
            tenant.setFirstName(rs.getString("first_name"));
            tenant.setLastName(rs.getString("last_name"));
            tenant.setMiddleName(rs.getString("middle_name"));
            tenant.setPhoneNumber(rs.getString("phone_number"));
            tenant.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }
}

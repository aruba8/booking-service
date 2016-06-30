package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.TenantDao;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Tenant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TenantDaoImpl extends BaseDao implements TenantDao {

    private final String SAVE_QUERY = "INSERT INTO tenant (first_name, last_name, middle_name, phone_number, email) VALUES (?, ?, ?, ?, ?);";

    private final String UPDATE_QUERY = "UPDATE tenant SET first_name=?, middle_name=?, last_name=?, phone_number=?, email=? WHERE id=?;";

    private final String DELETE_QUERY = "DELETE FROM tenant WHERE id=?;";

    private final String DELETE_ALL_QUERY = "DELETE FROM tenant;";

    private final String FIND_BY_EMAIL_QUERY = "SELECT * FROM tenant WHERE email = ?;";

    private final String GET_QUERY = "SELECT * FROM tenant WHERE id = ?;";

    public TenantDaoImpl(Connection connection) {
        super(connection);
    }

    public Tenant get(int key) throws DatabaseException {
        Tenant tenant = new Tenant();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = this.connection.prepareStatement(GET_QUERY);
            pstm.setInt(1, key);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;
            }
            tenant.setId(rs.getInt("id"));
            tenant.setFirstName(rs.getString("first_name"));
            tenant.setMiddleName(rs.getString("middle_name"));
            tenant.setLastName(rs.getString("last_name"));
            tenant.setPhoneNumber(rs.getString("phone_number"));
            tenant.setEmail(rs.getString("email"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return tenant;
    }

    public void save(Tenant tenant) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(SAVE_QUERY);
            pstm.setString(1, tenant.getFirstName());
            pstm.setString(2, tenant.getLastName());
            pstm.setString(3, tenant.getMiddleName());
            pstm.setString(4, tenant.getPhoneNumber());
            pstm.setString(5, tenant.getEmail());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void update(Tenant tenant) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(UPDATE_QUERY);
            pstm.setString(1, tenant.getFirstName());
            pstm.setString(2, tenant.getMiddleName());
            pstm.setString(3, tenant.getLastName());
            pstm.setString(4, tenant.getPhoneNumber());
            pstm.setString(5, tenant.getEmail());
            pstm.setInt(6, tenant.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }

    }

    public void delete(Tenant tenant) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(DELETE_QUERY);
            pstm.setInt(1, tenant.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }


    }

    public void deleteAll() throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(DELETE_ALL_QUERY);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }

    }

    public Tenant findByEmail(String email) throws DatabaseException {
        Tenant tenant = new Tenant();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = this.connection.prepareStatement(FIND_BY_EMAIL_QUERY);
            pstm.setString(1, email);
            rs = pstm.executeQuery();
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
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return tenant;
    }
}

package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.ApartmentDao;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDaoImpl extends BaseDao implements ApartmentDao {

    private final String SAVE_QUERY = "INSERT INTO apartment (address, price, landlord_id)  VALUES (?, ?, ?);";

    private final String GET_QUERY = "SELECT * FROM apartment WHERE id=?;";

    private final String FIND_BY_LANDLORD_ID_QUERY = "SELECT * FROM apartment WHERE landlord_id=?;";

    private final String UPDATE_QUERY = "UPDATE apartment SET address=?, price=?, landlord_id=? WHERE id=?;";

    private final String DELETE_QUERY = "DELETE FROM apartment WHERE id=?;";

    private final String GET_ALL_QUERY = "SELECT * FROM apartment;";

    private final String DELETE_ALL_QUERY = "DELETE FROM apartment;";


    public ApartmentDaoImpl(Connection connection) {
        super(connection);
    }

    public void save(Apartment apartment) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(SAVE_QUERY);
            pstm.setString(1, apartment.getAddress());
            pstm.setDouble(2, apartment.getPrice());
            pstm.setInt(3, apartment.getLandlordId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }

    }

    public Apartment get(int key) throws DatabaseException {
        Apartment apartment = new Apartment();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(GET_QUERY);
            pstm.setInt(1, key);
            rs = pstm.executeQuery();
            while (rs.next()) {
                apartment.setId(rs.getInt("id"));
                apartment.setAddress(rs.getString("address"));
                apartment.setPrice(rs.getDouble("price"));
                apartment.setLandlordId(rs.getInt("id"));
                apartment.setId(rs.getInt("landlord_id"));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return apartment;
    }

    public List<Apartment> findByLandlordId(int landlordId) throws DatabaseException {
        List<Apartment> apartmentList = new ArrayList<Apartment>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(FIND_BY_LANDLORD_ID_QUERY);
            pstm.setInt(1, landlordId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Apartment apartment = new Apartment();
                apartment.setAddress(rs.getString("address"));
                apartment.setId(rs.getInt("id"));
                apartment.setPrice(rs.getDouble("price"));
                apartment.setLandlordId(rs.getInt("landlord_id"));
                apartmentList.add(apartment);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return apartmentList;
    }


    public void update(Apartment apartment) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(UPDATE_QUERY);
            pstm.setString(1, apartment.getAddress());
            pstm.setDouble(2, apartment.getPrice());
            pstm.setInt(3, apartment.getLandlordId());
            pstm.setInt(4, apartment.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void delete(Apartment apartment) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(DELETE_QUERY);
            pstm.setInt(1, apartment.getId());
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

    public List<Apartment> getAll() throws DatabaseException {
        List<Apartment> apartmentList = new ArrayList<Apartment>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(GET_ALL_QUERY);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(rs.getInt("id"));
                apartment.setAddress(rs.getString("address"));
                apartment.setLandlordId(rs.getInt("landlord_id"));
                apartment.setPrice(rs.getDouble("price"));
                apartmentList.add(apartment);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return apartmentList;

    }

}

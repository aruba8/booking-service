package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.TransactionDao;
import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl extends BaseDao implements TransactionDao {


    private final String SAVE_QUERY = "INSERT INTO transaction (sum, apartment_id, tenant_id, booking_period_id, transaction_time) VALUES (?, ?, ?, ?, ?);";

    private final String GET_QUERY = "SELECT * FROM transaction WHERE id = ?;";

    private final String UPDATE_QUERY = "UPDATE transaction SET sum=?, apartment_id=?, tenant_id=?, booking_period_id=?, transaction_time=? WHERE id=?;";

    private final String DELETE_ALL_QUERY = "DELETE FROM transaction;";

    private final String FIND_BY_TENANT_ID_QUERY = "SELECT * FROM transaction WHERE tenant_id = ?;";

    private final String DELETE_QUERY = "DELETE FROM transaction WHERE id = ?;";

    public TransactionDaoImpl(Connection connection) {
        super(connection);
    }

    public void save(Transaction transaction) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(SAVE_QUERY);
            pstm.setDouble(1, transaction.getSum());
            pstm.setInt(2, transaction.getApartmentId());
            pstm.setInt(3, transaction.getTenantId());
            pstm.setInt(4, transaction.getBookingPeriodId());
            pstm.setDate(5, new Date(transaction.getTransactionTime().getTime()));
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public Transaction get(int key) throws DatabaseException {
        Transaction transaction = new Transaction();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = this.connection.prepareStatement(GET_QUERY);
            pstm.setInt(1, key);
            rs = pstm.executeQuery();
            rs.next();
            transaction.setId(key);
            transaction.setSum(rs.getDouble("sum"));
            transaction.setApartmentId(rs.getInt("apartment_id"));
            transaction.setBookingPeriodId(rs.getInt("booking_period_id"));
            transaction.setTenantId(rs.getInt("tenant_id"));
            transaction.setTransactionTime(rs.getTimestamp("transaction_time"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return transaction;
    }

    public void update(Transaction transaction) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(UPDATE_QUERY);
            pstm.setDouble(1, transaction.getSum());
            pstm.setInt(2, transaction.getApartmentId());
            pstm.setInt(3, transaction.getTenantId());
            pstm.setInt(4, transaction.getBookingPeriodId());
            pstm.setDate(5, new Date(transaction.getTransactionTime().getTime()));
            pstm.setInt(6, transaction.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public void delete(Transaction transaction) throws DatabaseException {
        PreparedStatement pstm = null;
        try {
            pstm = this.connection.prepareStatement(DELETE_QUERY);
            pstm.setInt(1, transaction.getId());
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
            pstm = this.connection.prepareStatement(DELETE_ALL_QUERY);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(null, pstm);
        }
    }

    public List<Transaction> findByTenantId(int tenantId) throws DatabaseException {
        Transaction transaction = new Transaction();
        List<Transaction> transactionList = new ArrayList<Transaction>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = this.connection.prepareStatement(FIND_BY_TENANT_ID_QUERY);
            pstm.setInt(1, tenantId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                transaction.setId(rs.getInt("id"));
                transaction.setSum(rs.getDouble("sum"));
                transaction.setApartmentId(rs.getInt("apartment_id"));
                transaction.setBookingPeriodId(rs.getInt("booking_period_id"));
                transaction.setTenantId(rs.getInt("tenant_id"));
                transaction.setTransactionTime(rs.getTimestamp("transaction_time"));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResources(rs, pstm);
        }
        return transactionList;
    }
}

package ca.erik.bs.dao.impl;

import ca.erik.bs.dao.TransactionDao;
import ca.erik.bs.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik Khalimov.
 */
public class TransactionDaoImpl implements TransactionDao {

    private final Connection connection;

    public TransactionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction (sum, apartment_id, tenant_id, booking_period_id, transaction_time) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setDouble(1, transaction.getSum());
            pstm.setInt(2, transaction.getApartmentId());
            pstm.setInt(3, transaction.getTenantId());
            pstm.setInt(4, transaction.getBookingPeriodId());
            pstm.setDate(5, new Date(transaction.getTransactionTime().getTime()));
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction get(int key) {
        String sql = "SELECT * FROM transaction WHERE id = ?;";
        Transaction transaction = new Transaction();
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setInt(1, key);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            transaction.setId(key);
            transaction.setSum(rs.getDouble("sum"));
            transaction.setApartmentId(rs.getInt("apartment_id"));
            transaction.setBookingPeriodId(rs.getInt("booking_period_id"));
            transaction.setTenantId(rs.getInt("tenant_id"));
            transaction.setTransactionTime(rs.getTimestamp("transaction_time"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public void update(Transaction transaction) {
        String sql = "UPDATE transaction SET sum=?, apartment_id=?, tenant_id=?, booking_period_id=?, transaction_time=? WHERE id=?;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setDouble(1, transaction.getSum());
            pstm.setInt(2, transaction.getApartmentId());
            pstm.setInt(3, transaction.getTenantId());
            pstm.setInt(4, transaction.getBookingPeriodId());
            pstm.setDate(5, new Date(transaction.getTransactionTime().getTime()));
            pstm.setInt(6, transaction.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Transaction transaction) {
        String sqlq = "DELETE FROM transaction WHERE id = ?;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sqlq);
            pstm.setInt(1, transaction.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        String sqlq = "DELETE FROM transaction;";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sqlq);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> findByTenantId(int tenantId) {
        String sql = "SELECT * FROM transaction WHERE tenant_id = ?;";
        Transaction transaction = new Transaction();
        List<Transaction> transactionList = new ArrayList<Transaction>();
        try {
            PreparedStatement pstm = this.connection.prepareStatement(sql);
            pstm.setInt(1, tenantId);
            ResultSet rs = pstm.executeQuery();
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
            e.printStackTrace();
        }
        return transactionList;
    }
}

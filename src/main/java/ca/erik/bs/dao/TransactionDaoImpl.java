package ca.erik.bs.dao;

import ca.erik.bs.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            pstm.setTimestamp(5, transaction.getTransactionTime());
            pstm.execute();

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
            pstm.setTimestamp(5, transaction.getTransactionTime());
            pstm.setInt(6, transaction.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Transaction transaction) {

    }
}

package ca.erik.bs.model;

import java.sql.Timestamp;

/**
 * @author Erik Khalimov.
 */
public class Transaction {

    private int id;
    private double sum;
    private int tenantId;
    private int apartmentId;
    private int bookingPeriodId;
    private Timestamp transactionTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public int getBookingPeriodId() {
        return bookingPeriodId;
    }

    public void setBookingPeriodId(int bookingPeriodId) {
        this.bookingPeriodId = bookingPeriodId;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }
}

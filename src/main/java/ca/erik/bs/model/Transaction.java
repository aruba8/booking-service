package ca.erik.bs.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Erik Khalimov.
 */
public class Transaction implements Serializable {

    private int id;
    private double sum;
    private int tenantId;
    private int apartmentId;
    private int bookingPeriodId;
    private Date transactionTime;

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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }
}

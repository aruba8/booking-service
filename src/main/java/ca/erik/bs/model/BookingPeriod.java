package ca.erik.bs.model;


import java.io.Serializable;
import java.sql.Date;

/**
 * @author Erik Khalimov.
 */
public class BookingPeriod implements Serializable {

    private int id;
    private Date fromDate;
    private Date toDate;
    private int apartment_id;

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }
}

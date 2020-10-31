package ca.erik.bs.model;

import java.io.Serializable;

/**
 * @author Erik Khalimov.
 */
public class Apartment implements Serializable{

    private int id;
    private double price;
    private String address;
    private int landlordId;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(int landlordId) {
        this.landlordId = landlordId;
    }
}

package ca.erik.bs.dao;

import ca.erik.bs.model.Apartment;

import java.util.List;

/**
 * @author Erik Khalimov.
 */
public interface ApartmentDao {

    void save(Apartment apartment);

    Apartment get(int key);

    List<Apartment> findByLandlordId(int landlordId);

    void update(Apartment apartment);

    void delete(Apartment apartment);

    void deleteAll();

    List<Apartment> getAll();

}

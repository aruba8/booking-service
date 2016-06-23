package ca.erik.bs.dao;

import ca.erik.bs.model.Apartment;

import java.util.List;

/**
 * @author Erik Khalimov.
 */
public interface ApartmentDao {

    void save(Apartment apartment);

    Apartment get(int key);

    void update(Apartment apartment);

    void delete(Apartment apartment);

    List<Apartment> getAll();

}

package ca.erik.bs.dao;

import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Apartment;

import java.util.List;

public interface ApartmentDao {

    void save(Apartment apartment) throws DatabaseException;

    Apartment get(int key) throws DatabaseException;

    List<Apartment> findByLandlordId(int landlordId) throws DatabaseException;

    void update(Apartment apartment) throws DatabaseException;

    void delete(Apartment apartment) throws DatabaseException;

    void deleteAll() throws DatabaseException;

    List<Apartment> getAll() throws DatabaseException;

}

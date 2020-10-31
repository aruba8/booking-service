package ca.erik.bs.dao;

import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Landlord;

import java.util.List;

public interface LandlordDao {

    Landlord get(int key) throws DatabaseException;

    Landlord findLandlordByEmail(String email) throws DatabaseException;

    void save(Landlord landLord) throws DatabaseException;

    void update(Landlord landLord) throws DatabaseException;

    void delete(Landlord landLord) throws DatabaseException;

    void deleteAll() throws DatabaseException;

    List<Landlord> findAll() throws DatabaseException;
}

package ca.erik.bs.dao;

import ca.erik.bs.model.Landlord;

/**
 * @author Erik Khalimov.
 */
public interface LandlordDao {

    Landlord get(int key);

    void save(Landlord landLord);

    void update(Landlord landLord);

    void delete(Landlord landLord);
}

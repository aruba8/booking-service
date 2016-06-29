package ca.erik.bs.dao;

import ca.erik.bs.model.Landlord;

import java.util.List;

public interface LandlordDao {

    Landlord get(int key);

    Landlord findLandlordByEmail(String email);

    void save(Landlord landLord);

    void update(Landlord landLord);

    void delete(Landlord landLord);

    void deleteAll();

    List<Landlord> findAll();
}

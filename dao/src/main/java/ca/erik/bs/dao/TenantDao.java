package ca.erik.bs.dao;

import ca.erik.bs.model.Tenant;

/**
 * @author Erik Khalimov.
 */
public interface TenantDao {

    Tenant get(int key);

    void save(Tenant tenant);

    void update(Tenant tenant);

    void delete(Tenant tenant);

    void deleteAll();

    Tenant findByEmail(String email);
}

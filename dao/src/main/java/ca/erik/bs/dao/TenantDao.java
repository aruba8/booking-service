package ca.erik.bs.dao;

import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Tenant;

public interface TenantDao {

    Tenant get(int key) throws DatabaseException;

    void save(Tenant tenant) throws DatabaseException;

    void update(Tenant tenant) throws DatabaseException;

    void delete(Tenant tenant) throws DatabaseException;

    void deleteAll() throws DatabaseException;

    Tenant findByEmail(String email) throws DatabaseException;
}

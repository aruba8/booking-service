package ca.erik.bs.dao;

import ca.erik.bs.dao.exception.DatabaseException;
import ca.erik.bs.model.Transaction;

import java.util.List;

public interface TransactionDao {

    void save(Transaction transaction) throws DatabaseException;

    Transaction get(int key) throws DatabaseException;

    void update(Transaction transaction) throws DatabaseException;

    void delete(Transaction transaction) throws DatabaseException;

    void deleteAll() throws DatabaseException;

    List<Transaction> findByTenantId(int tenantId) throws DatabaseException;
}

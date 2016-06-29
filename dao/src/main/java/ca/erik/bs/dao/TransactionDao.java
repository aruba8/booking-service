package ca.erik.bs.dao;

import ca.erik.bs.model.Transaction;

import java.util.List;

public interface TransactionDao {

    void save(Transaction transaction);

    Transaction get(int key);

    void update(Transaction transaction);

    void delete(Transaction transaction);

    void deleteAll();

    List<Transaction> findByTenantId(int tenantId);
}

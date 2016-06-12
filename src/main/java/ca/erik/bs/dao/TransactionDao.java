package ca.erik.bs.dao;

import ca.erik.bs.model.Transaction;

/**
 * @author Erik Khalimov.
 */
public interface TransactionDao {

    void save(Transaction transaction);

    Transaction get(int key);

    void update(Transaction transaction);

    void delete(Transaction transaction);

}

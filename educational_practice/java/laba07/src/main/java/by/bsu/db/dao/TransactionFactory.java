package by.bsu.db.dao;

import by.bsu.db.dao.exception.DAOException;

public interface TransactionFactory {
    Transaction createTransaction() throws DAOException;

    void close();
}

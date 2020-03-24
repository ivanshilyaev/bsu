package by.bsu.db.dao;

import by.bsu.db.dao.exception.DAOException;

public interface Transaction {
    <Type extends Dao<?, ?>> Type createDao(Class<Type> key) throws DAOException;

    void commit() throws DAOException;

    void rollback() throws DAOException;
}

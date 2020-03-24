package by.bsu.db.dao;

import by.bsu.db.bean.Entity;
import by.bsu.db.dao.exception.DAOException;

import java.util.List;

public interface Dao<K, T extends Entity> {
    public abstract List<T> findAll() throws DAOException;

    public abstract T findEntityById(K id) throws DAOException; // read

    public abstract boolean delete(K id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity);

    public abstract T update(T entity);
}

package by.bsu.db.dao.mysql;

import by.bsu.db.dao.*;
import by.bsu.db.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Map<Class<? extends Dao<?, ?>>, Class<? extends DaoImpl>> classes =
            new ConcurrentHashMap<>();

    static {
        classes.put(SectionDao.class, SectionDaoImpl.class);
        classes.put(ReportDao.class, ReportDaoImpl.class);
        classes.put(PresenterDao.class, PresenterDaoImpl.class);
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <Type extends Dao<?, ?>> Type createDao(Class<Type> key) throws DAOException {
        Class<? extends DaoImpl> value = classes.get(key);
        if (value != null) {
            try {
                DaoImpl dao = value.newInstance();
                dao.setConnection(connection);
                return (Type) dao;
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Impossible to create data access object", e);
                throw new DAOException(e);
            }
        }
        return null;
    }

    @Override
    public void commit() throws DAOException {

    }

    @Override
    public void rollback() throws DAOException {

    }
}

package by.bsu.db.dao.mysql;

import by.bsu.db.dao.Transaction;
import by.bsu.db.dao.TransactionFactory;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.ConnectorDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private static Logger LOGGER = LogManager.getLogger();

    private Connection connection;

    public TransactionFactoryImpl() throws DAOException {
        try {
            connection = ConnectorDB.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Couldn't create connection to database");
            throw new DAOException(e.getCause());
        }
    }

    @Override
    public Transaction createTransaction() throws DAOException {
        return new TransactionImpl(connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
}

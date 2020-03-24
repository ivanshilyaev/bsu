package by.bsu.db.dao.mysql;

import by.bsu.db.bean.Entity;
import by.bsu.db.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DaoImpl<K, T extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void close(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            LOGGER.error("Couldn't close statement", e);
        }
    }

    public void closeConnection() throws DAOException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException("Couldn't read data");
            }
        }
    }
}

package by.bsu.db.dao.mysql;

import by.bsu.db.bean.Presenter;
import by.bsu.db.bean.Report;
import by.bsu.db.dao.PresenterDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.ConnectorDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PresenterDaoImpl extends DaoImpl<String, Presenter> implements PresenterDao {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String SQL_SELECT_ALL_PRESENTERS =
            "SELECT surname, name, patronymic, report_id FROM presenter;";

    @Override
    public List<Presenter> findAll() throws DAOException {
        List<Presenter> presenters = new ArrayList<>();
        try {
            // fix!
            connection = ConnectorDB.getConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = null;
                try {
                    resultSet = statement.executeQuery(SQL_SELECT_ALL_PRESENTERS);
                    while (resultSet.next()) {
                        Presenter presenter = new Presenter();
                        presenter.setSurname(resultSet.getString(1));
                        presenter.setName(resultSet.getString(2));
                        presenter.setPatronymic(resultSet.getString(3));
                        int reportID = resultSet.getInt(4);
                        Report report = new ReportDaoImpl().findEntityById(reportID);
                        if (report != null) {
                            presenter.setReport(report);
                        }
                        presenters.add(presenter);
                    }
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                }
            } finally {
                if (statement != null) {
                    close(statement);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("DB connection error", e);
        } finally {
            closeConnection();
        }
        return presenters;
    }

    @Override
    public Presenter findEntityById(String id) throws DAOException {
        throw new DAOException("Operation is not supported");
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean delete(Presenter entity) {
        return false;
    }

    @Override
    public boolean create(Presenter entity) {
        return false;
    }

    @Override
    public Presenter update(Presenter entity) {
        return null;
    }

    @Override
    public Presenter findPresenterBySurname(String surname) throws DAOException {
        Presenter presenter = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectorDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_PRESENTERS);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(surname)) {
                    presenter = new Presenter();
                    presenter.setSurname(resultSet.getString(1));
                    presenter.setName(resultSet.getString(2));
                    presenter.setPatronymic(resultSet.getString(3));
                    int reportID = resultSet.getInt(4);
                    Report report = new ReportDaoImpl().findEntityById(reportID);
                    if (report != null) {
                        presenter.setReport(report);
                    }
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("Couldn't create statement", e);
        } finally {
            if (statement != null) {
                close(statement);
            }
        }
        return presenter;
    }
}

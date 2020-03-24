package by.bsu.db.dao.mysql;

import by.bsu.db.bean.Report;
import by.bsu.db.bean.Section;
import by.bsu.db.dao.ReportDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.ConnectorDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl extends DaoImpl<Integer, Report> implements ReportDao {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String SQL_SELECT_ALL_REPORTS =
            "SELECT id, name, section_id FROM report;";

    @Override
    public List<Report> findAll() throws DAOException {
        List<Report> reports = new ArrayList<>();
        try {
            connection = ConnectorDB.getConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = null;
                try {
                    resultSet = statement.executeQuery(SQL_SELECT_ALL_REPORTS);
                    while (resultSet.next()) {
                        Report report = new Report();
                        report.setId(resultSet.getInt(1));
                        report.setName(resultSet.getString(2));
                        int sectionID = resultSet.getInt(3);
                        Section section = new SectionDaoImpl().findEntityById(sectionID);
                        if (section != null) {
                            report.setSection(section);
                        }
                        reports.add(report);
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
        return reports;
    }

    @Override
    public Report findEntityById(Integer id) throws DAOException {
        Report report = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = ConnectorDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_REPORTS);
            while (resultSet.next()) {
                if (resultSet.getInt(1) == id) {
                    report = new Report();
                    report.setId(resultSet.getInt(1));
                    report.setName(resultSet.getString(2));
                    int sectionID = resultSet.getInt(3);
                    Section section = new SectionDaoImpl().findEntityById(sectionID);
                    if (section != null) {
                        report.setSection(section);
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
        return report;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Report entity) {
        return false;
    }

    @Override
    public boolean create(Report entity) {
        return false;
    }

    @Override
    public Report update(Report entity) {
        return null;
    }
}

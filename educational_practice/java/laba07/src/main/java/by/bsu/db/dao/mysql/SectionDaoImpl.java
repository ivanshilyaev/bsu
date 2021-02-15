package by.bsu.db.dao.mysql;

import by.bsu.db.bean.Section;
import by.bsu.db.dao.SectionDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.ConnectorDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SectionDaoImpl extends DaoImpl<Integer, Section> implements SectionDao {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String SQL_SELECT_ALL_SECTIONS =
            "SELECT id, name, judges FROM section;";

    @Override
    public List<Section> findAll() throws DAOException {
        List<Section> sections = new ArrayList<>();
        try {
            Statement statement = null;
            try {
                ResultSet resultSet = null;
                try {
                    resultSet = statement.executeQuery(SQL_SELECT_ALL_SECTIONS);
                    while (resultSet.next()) {
                        Section section = new Section();
                        section.setId(resultSet.getInt(1));
                        section.setName(resultSet.getString(2));
                        section.setJudges(resultSet.getInt(3));
                        sections.add(section);
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
        return sections;
    }

    @Override
    public Section findEntityById(Integer id) throws DAOException {
        Section section = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(SQL_SELECT_ALL_SECTIONS);
            while (resultSet.next()) {
                if (resultSet.getInt(1) == id) {
                    section = new Section();
                    section.setId(resultSet.getInt(1));
                    section.setName(resultSet.getString(2));
                    section.setJudges(resultSet.getInt(3));
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
        return section;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(Section entity) {
        return false;
    }

    @Override
    public boolean create(Section entity) {
        return false;
    }

    @Override
    public Section update(Section entity) {
        return null;
    }
}

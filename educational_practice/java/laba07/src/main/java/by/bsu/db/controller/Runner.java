package by.bsu.db.controller;

import by.bsu.db.dao.SectionDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.dao.mysql.SectionDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            SectionDao sectionDao = new SectionDaoImpl();
            System.out.println(sectionDao.findAll());
        } catch (DAOException e) {
            LOGGER.error("Failure");
        }
    }
}

package by.bsu.db.controller;

import by.bsu.db.dao.PresenterDao;
import by.bsu.db.dao.ReportDao;
import by.bsu.db.dao.SectionDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.dao.mysql.PresenterDaoImpl;
import by.bsu.db.dao.mysql.ReportDaoImpl;
import by.bsu.db.dao.mysql.SectionDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            PresenterDao presenterDao = new PresenterDaoImpl();
            System.out.println(presenterDao.findAll());
            System.out.println(presenterDao.findPresenterBySurname("Шиляев"));
        } catch (DAOException e) {
            LOGGER.error("Failure");
        }
    }
}

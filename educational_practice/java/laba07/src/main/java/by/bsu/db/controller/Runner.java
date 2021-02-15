package by.bsu.db.controller;

import by.bsu.db.dao.PresenterDao;
import by.bsu.db.dao.ReportDao;
import by.bsu.db.dao.SectionDao;
import by.bsu.db.dao.TransactionFactory;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.dao.mysql.PresenterDaoImpl;
import by.bsu.db.dao.mysql.ReportDaoImpl;
import by.bsu.db.dao.mysql.SectionDaoImpl;
import by.bsu.db.dao.mysql.TransactionFactoryImpl;
import by.bsu.db.service.*;
import by.bsu.db.service.exception.ServiceException;
import by.bsu.db.view.MainFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
            PresenterService presenterService = serviceFactory.getService(PresenterService.class);
            System.out.println(presenterService.findAll());
        } catch (ServiceException | DAOException e) {
            e.printStackTrace();
        }

        //MainFrame mainFrame = new MainFrame("Conference");
    }
}

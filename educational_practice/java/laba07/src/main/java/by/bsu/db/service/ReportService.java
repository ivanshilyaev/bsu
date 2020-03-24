package by.bsu.db.service;

import by.bsu.db.bean.Report;
import by.bsu.db.service.exception.ServiceException;

import java.util.List;

public interface ReportService extends Service {
    List<Report> findAll() throws ServiceException;

    Report findSectionById(int id) throws ServiceException;
}

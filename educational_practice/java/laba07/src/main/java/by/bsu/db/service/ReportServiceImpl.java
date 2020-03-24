package by.bsu.db.service;

import by.bsu.db.bean.Report;
import by.bsu.db.dao.ReportDao;
import by.bsu.db.dao.SectionDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.exception.ServiceException;

import java.util.List;

public class ReportServiceImpl extends ServiceImpl implements ReportService {
    @Override
    public List<Report> findAll() throws ServiceException {
        try {
            ReportDao reportDao = transaction.createDao(ReportDao.class);
            return reportDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getCause());
        }
    }

    @Override
    public Report findSectionById(int id) throws ServiceException {
        try {
            ReportDao reportDao = transaction.createDao(ReportDao.class);
            return reportDao.findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getCause());
        }
    }
}

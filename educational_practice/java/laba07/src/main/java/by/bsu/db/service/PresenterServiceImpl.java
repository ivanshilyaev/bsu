package by.bsu.db.service;

import by.bsu.db.bean.Presenter;
import by.bsu.db.dao.PresenterDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.exception.ServiceException;

import java.util.List;

public class PresenterServiceImpl extends ServiceImpl implements PresenterService {
    @Override
    public List<Presenter> findAll() throws ServiceException {
        try {
            PresenterDao presenterDao = transaction.createDao(PresenterDao.class);
            return presenterDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getCause());
        }
    }

    @Override
    public Presenter findPresenterBySurname(String surname) throws ServiceException {
        try {
            PresenterDao presenterDao = transaction.createDao(PresenterDao.class);
            return presenterDao.findPresenterBySurname(surname);
        } catch (DAOException e) {
            throw new ServiceException(e.getCause());
        }
    }
}

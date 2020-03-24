package by.bsu.db.service;

import by.bsu.db.bean.Presenter;
import by.bsu.db.service.exception.ServiceException;

import java.util.List;

public interface PresenterService extends Service {
    List<Presenter> findAll() throws ServiceException;

    Presenter findPresenterBySurname(String surname) throws ServiceException;
}

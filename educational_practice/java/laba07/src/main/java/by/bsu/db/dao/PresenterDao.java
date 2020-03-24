package by.bsu.db.dao;

import by.bsu.db.bean.Presenter;
import by.bsu.db.dao.exception.DAOException;

public interface PresenterDao extends Dao<String, Presenter> {
    // findEntityById is replaced for find presenter by surname
    public Presenter findPresenterBySurname(String surname) throws DAOException;
}

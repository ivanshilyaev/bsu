package by.bsu.db.service;

import by.bsu.db.service.exception.ServiceException;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key) throws ServiceException;

    void close();
}

package by.bsu.db.service;

import by.bsu.db.service.exception.ServiceException;

public class ServiceFactoryImpl implements ServiceFactory {
    @Override
    public <Type extends Service> Type getService(Class<Type> key) throws ServiceException {
        return null;
        // to do
    }

    @Override
    public void close() {

    }
}

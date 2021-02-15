package by.bsu.db.service;

import by.bsu.db.dao.Transaction;
import by.bsu.db.dao.TransactionFactory;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Map<Class<? extends Service>, Class<? extends ServiceImpl>> services
            = new ConcurrentHashMap<>();

    static {
        services.put(SectionService.class, SectionServiceImpl.class);
        services.put(ReportService.class, ReportServiceImpl.class);
        services.put(PresenterService.class, PresenterServiceImpl.class);
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) throws ServiceException {
        this.factory = factory;
    }

    @Override
    public <Type extends Service> Type getService(Class<Type> key) throws ServiceException {
        Class<? extends ServiceImpl> value = services.get(key);
        if (value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                Transaction transaction = factory.createTransaction();
                ServiceImpl service = value.newInstance();
                service.setTransaction(transaction);
                InvocationHandler handler = new ServiceInvocationHandlerImpl(service);
                return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("It is impossible to instance service class", e);
                throw new ServiceException(e);
            } catch (DAOException e) {
                LOGGER.error("Couldn't create service");
                throw new ServiceException(e);
            }
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}

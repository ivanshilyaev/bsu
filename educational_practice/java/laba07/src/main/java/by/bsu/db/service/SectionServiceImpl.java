package by.bsu.db.service;

import by.bsu.db.bean.Section;
import by.bsu.db.dao.SectionDao;
import by.bsu.db.dao.exception.DAOException;
import by.bsu.db.service.exception.ServiceException;

import java.util.List;

public class SectionServiceImpl extends ServiceImpl implements SectionService {

    @Override
    public List<Section> findAll() throws ServiceException {
        try {
            SectionDao sectionDao = transaction.createDao(SectionDao.class);
            return sectionDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getCause());
        }
    }

    @Override
    public Section findSectionById(int id) throws ServiceException {
        try {
            SectionDao sectionDao = transaction.createDao(SectionDao.class);
            return sectionDao.findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getCause());
        }
    }
}

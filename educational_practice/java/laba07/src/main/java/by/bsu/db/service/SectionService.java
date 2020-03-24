package by.bsu.db.service;

import by.bsu.db.bean.Section;
import by.bsu.db.service.exception.ServiceException;

import java.util.List;

public interface SectionService extends Service {
    List<Section> findAll() throws ServiceException;

    Section findSectionById(int id) throws ServiceException;
}

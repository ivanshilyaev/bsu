package by.bsu.tasks.laba01.service;

import by.bsu.tasks.laba01.service.exception.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Searcher {
    public void search(String regex, String stringToFind, String directoryName,
                       ExecutorService service) throws ServiceException {
        File sourceDir = new File(directoryName);
        if (!sourceDir.isDirectory()) {
            throw new ServiceException("Given path is not a directory");
        }
        File[] files = sourceDir.listFiles();
        if (files == null) {
            throw new ServiceException("No files in the directory");
        }
        service.execute(new FileExecutor(files, regex, stringToFind,
                new ArrayList<>(), service));
    }
}

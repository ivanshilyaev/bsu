package by.bsu.tasks.laba_1.service;

import by.bsu.tasks.laba_1.dao.FileReaderDAO;
import by.bsu.tasks.laba_1.dao.exception.DAOException;
import by.bsu.tasks.laba_1.service.exception.ServiceException;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {
    private void checkFiles(File[] files, String regex, String stringToFind,
                            List<File> list) throws ServiceException {
        Pattern pattern = Pattern.compile(regex);
        FileReaderDAO reader = new FileReaderDAO();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    search(regex, stringToFind, file.getAbsolutePath(), list);
                }
                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.find()) {
                    String[] lines = reader.read(file);
                    for (String line : lines) {
                        if (line.equals(stringToFind)) {
                            list.add(file);
                        }
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Error while reading from file", e.getCause());
        }
    }

    public void search(String regex, String stringToFind, String directoryName,
                       List<File> list) throws ServiceException {
        File sourceDir = new File(directoryName);
        if (!sourceDir.isDirectory()) {
            throw new ServiceException("Given path is not a directory");
        }
        File[] files = sourceDir.listFiles();
        if (files == null) {
            throw new ServiceException("No files in the directory");
        }
        checkFiles(files, regex, stringToFind, list);
    }
}

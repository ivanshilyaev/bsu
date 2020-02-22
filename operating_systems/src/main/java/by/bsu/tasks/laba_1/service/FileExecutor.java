package by.bsu.tasks.laba_1.service;

import by.bsu.tasks.laba_1.dao.FileReaderDAO;
import by.bsu.tasks.laba_1.dao.exception.DAOException;
import by.bsu.tasks.laba_1.service.exception.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExecutor extends Thread {
    private static int nextID = 0;

    private File[] files;
    private String regex;
    private String stringToFind;
    private List<File> list;

    public FileExecutor(File[] files, String regex, String stringToFind, List<File> list) {
        super("Executor " + (++nextID));
        this.files = files;
        this.regex = regex;
        this.stringToFind = stringToFind;
        this.list = list;
    }

    private void checkFiles(File[] files, String regex,
                            List<File> list) throws ServiceException {
        Pattern pattern = Pattern.compile(regex);
        FileReaderDAO reader = new FileReaderDAO();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    new FileExecutor(file.listFiles(), regex, stringToFind, new ArrayList<>()).start();
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

    @Override
    public void run() {
        try {
            checkFiles(files, regex, list);
            for (File file : list) {
                System.out.println(file.getPath());
            }
            System.out.println(getName() + " finished his work");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}

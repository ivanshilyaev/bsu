package by.bsu.tasks.laba01.service;

import by.bsu.tasks.laba01.dao.FileReaderDAO;
import by.bsu.tasks.laba01.dao.exception.DAOException;
import by.bsu.tasks.laba01.service.exception.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExecutor implements Runnable {
    private File[] files;
    private String regex;
    private String stringToFind;
    private List<File> list;
    ExecutorService service;

    public FileExecutor(File[] files, String regex, String stringToFind,
                        List<File> list, ExecutorService service) {
        this.files = files;
        this.regex = regex;
        this.stringToFind = stringToFind;
        this.list = list;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            Pattern pattern = Pattern.compile(regex);
            FileReaderDAO reader = new FileReaderDAO();
            try {
                for (File file : files) {
                    if (file.isDirectory()) {
                        service.execute(new FileExecutor(file.listFiles(), regex,
                                stringToFind, new ArrayList<>(), service));
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

            for (File file : list) {
                System.out.println(file.getPath());
            }
            System.out.println(Thread.currentThread().getName() + " finished his work");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}

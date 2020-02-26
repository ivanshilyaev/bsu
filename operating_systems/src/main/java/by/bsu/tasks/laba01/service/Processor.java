package by.bsu.tasks.laba01.service;

import by.bsu.tasks.laba01v2.dao.FileReaderDAO;
import by.bsu.tasks.laba01v2.dao.exception.DAOException;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor implements Runnable {
    private File file;
    private String regex;
    private String stringToFind;
    private List<File> list;

    public Processor(File file, String regex, String stringToFind, List<File> list) {
        this.file = file;
        this.regex = regex;
        this.stringToFind = stringToFind;
        this.list = list;
    }

    @Override
    public void run() {
        Pattern pattern = Pattern.compile(regex);
        FileReaderDAO reader = new FileReaderDAO();
        try {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()) {
                String[] lines = reader.read(file);
                for (String line : lines) {
                    // if (line.equals(stringToFind)) {
                    if (line.contains(stringToFind)) {
                        list.add(file);
                    }
                }
            }
            for (File file : list) {
                System.out.println(file.getPath());
            }
            //System.out.println(Thread.currentThread().getName() + " finished his work");
        } catch (DAOException e) {
            System.err.println(e.getMessage());
        }
    }
}

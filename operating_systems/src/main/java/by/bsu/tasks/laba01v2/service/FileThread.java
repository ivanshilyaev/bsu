package by.bsu.tasks.laba01v2.service;

import by.bsu.tasks.laba01v2.dao.FileReaderDAO;
import by.bsu.tasks.laba01v2.dao.exception.DAOException;
import by.bsu.tasks.laba01v2.view.FileFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileThread implements Runnable {
    private String regex;
    private String stringToFind;
    private String primaryDirectoryName;
    private Pattern pattern;
    private List<File> list;

    public FileThread(String regex, String stringToFind, String directoryName) {
        this.regex = regex;
        this.stringToFind = stringToFind;
        this.primaryDirectoryName = directoryName;
        pattern = Pattern.compile(regex);
        list = new ArrayList<>();

        FileFrame frame = new FileFrame("Thread", this);
    }

    public void search(String directoryName) {
        File sourceDir = new File(directoryName);
        File[] files = sourceDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                search(file.getAbsolutePath());
            }
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
            } catch (DAOException e) {
                System.err.println("Can't read from file");
            }
        }
    }

    @Override
    public void run() {

        search(primaryDirectoryName);
        for (File file : list) {
            System.out.println(file.getPath());
        }
        System.out.println(Thread.currentThread().getName() + " has finished its work");
    }
}

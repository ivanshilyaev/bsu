package by.bsu.tasks.laba01v2.service;

import by.bsu.tasks.laba01v2.dao.FileReaderDAO;
import by.bsu.tasks.laba01v2.dao.exception.DAOException;
import by.bsu.tasks.laba01v2.view.FileFrame;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileThread implements Runnable {
    private String regex;
    private String stringToFind;
    private String primaryDirectoryName;
    private Pattern pattern;
    private List<File> list;

    public FileThread() {
        list = new ArrayList<>();
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
        FileFrame frame = new FileFrame("Thread", this);
        while (frame.isPaused) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        regex = frame.textField1.getText();
        stringToFind = frame.textField2.getText();
        primaryDirectoryName = frame.textField3.getText();
        pattern = Pattern.compile(regex);

        search(primaryDirectoryName);
        Document document = frame.textField4.getDocument();
        try {
            document.remove(0, document.getLength());
            for (File file : list) {
                document.insertString(0, file.getAbsolutePath(), null);
                //System.out.println(file.getPath());
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " has finished its work");
    }
}

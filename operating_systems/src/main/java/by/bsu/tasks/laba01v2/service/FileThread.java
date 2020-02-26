package by.bsu.tasks.laba01v2.service;

import by.bsu.tasks.laba01v2.dao.FileReaderDAO;
import by.bsu.tasks.laba01v2.dao.exception.DAOException;
import by.bsu.tasks.laba01v2.view.FileFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FileThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    // regex for filename
    // ^[\w,\s-]+\.[A-Za-z]{3}$

    private String regex;
    private String stringToFind;
    private String primaryDirectoryName;
    private Pattern pattern;
    private List<File> list;

    private FileFrame frame;
    // for writing output
    private Document document;

    public FileThread() {
        list = new ArrayList<>();
        frame = new FileFrame("Thread", this);
        document = frame.jTextArea.getDocument();
        try {
            document.remove(0, document.getLength());
        } catch (BadLocationException e) {
            LOGGER.error("BadLocationException in constructor");
        }
    }

    public void search(String directoryName) {
        File sourceDir = new File(directoryName);
        File[] files = sourceDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (!file.exists()) {
                return;
            }
            if (file.isDirectory() && frame.jCheckBox.isSelected()) {
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
                            if (frame.isPaused) {
                                pause();
                            }
                            if (frame.isStopped) {
                                return;
                            }
                            document.insertString(document.getLength(),
                                    file.getAbsolutePath() + "\r\n", null);
                            TimeUnit.MILLISECONDS.sleep(50);
                        }
                    }
                }
            } catch (DAOException e) {
                LOGGER.error("Can't read from file");
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException in search method");
            } catch (BadLocationException e) {
                LOGGER.error("BadLocationException in search method");
            }
        }
    }

    public void pause() {
        while (frame.isPaused) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException in pause method");
            }
        }
    }

    @Override
    public void run() {
        frame.setTitle(Thread.currentThread().getName());
        // waiting for Start button
        pause();

        regex = frame.textField1.getText();
        stringToFind = frame.textField2.getText();
        primaryDirectoryName = frame.textField3.getText();
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            try {
                document.insertString(document.getLength(),
                        "Illegal regex!\r\n", null);
                return;
            } catch (BadLocationException ex) {
                LOGGER.error("BadLocationException on finish");
            }
        }

        search(primaryDirectoryName);
        try {
            if (list.isEmpty()) {
                document.insertString(document.getLength(),
                        "No such files!\r\n", null);
            } else {
                document.insertString(document.getLength(),
                        "Number of files: " + list.size() + " \r\n", null);
            }
        } catch (BadLocationException e) {
            LOGGER.error("BadLocationException on finish");
        }
        System.out.println(Thread.currentThread().getName() + " has finished its work");
    }
}

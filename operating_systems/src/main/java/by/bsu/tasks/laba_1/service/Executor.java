package by.bsu.tasks.laba_1.service;

import by.bsu.tasks.laba_1.service.exception.ServiceException;

import java.io.File;
import java.util.List;

public class Executor extends Thread {
    private static int nextID = 0;

    private String regex;
    private String stringToFind;
    private String dirName;
    private List<File> list;

    public Executor(String regex, String stringToFind, String dirName, List<File> list) {
        super("Executor " + (++nextID));
        this.regex = regex;
        this.stringToFind = stringToFind;
        this.dirName = dirName;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            Searcher searcher = new Searcher();
            searcher.search(regex, stringToFind, dirName, list);
            if (!list.isEmpty()) {
                for (File file : list) {
                    System.out.println(file.getName());
                }
            }
            System.out.println(getName() + " finished his work");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}

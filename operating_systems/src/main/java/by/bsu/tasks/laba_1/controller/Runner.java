package by.bsu.tasks.laba_1.controller;

import by.bsu.tasks.laba_1.service.Searcher;
import by.bsu.tasks.laba_1.service.exception.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regex = args[0];
        String stringToFind = args[1];
        String dirName = args[2];

        List<File> list = new ArrayList<>();
        try {
            Searcher searcher = new Searcher();
            searcher.search(regex, stringToFind, dirName, list);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
        if (!list.isEmpty()) {
            for (File file : list) {
                System.out.println(file.getName());
            }
        }
    }
}

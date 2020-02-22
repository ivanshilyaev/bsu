package by.bsu.tasks.laba_1.controller;

import by.bsu.tasks.laba_1.service.FileExecutor;
import by.bsu.tasks.laba_1.service.Searcher;
import by.bsu.tasks.laba_1.service.exception.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {
    private static final int NUM = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regex = args[0];
        String stringToFind = args[1];
        String dirName = args[2];

        Searcher searcher = new Searcher();
        try {
            searcher.search(regex, stringToFind, dirName);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Main finished its work");
    }
}

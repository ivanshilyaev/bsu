package by.bsu.tasks.laba01.controller;

import by.bsu.tasks.laba01.service.Searcher;
import by.bsu.tasks.laba01.service.exception.ServiceException;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Runner {
    private static final int POOL_SIZE = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regex = args[0];
        String stringToFind = args[1];
        String dirName = args[2];

        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
        Searcher searcher = new Searcher();
        try {
            searcher.search(regex, stringToFind, dirName, service);
            service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
        System.out.println("Main finished its work");
    }
}

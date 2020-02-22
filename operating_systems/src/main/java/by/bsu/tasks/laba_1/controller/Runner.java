package by.bsu.tasks.laba_1.controller;

import by.bsu.tasks.laba_1.service.Executor;

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
        List<File> list = new ArrayList<>();

        List<Executor> threads = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            threads.add(new Executor(regex, stringToFind, dirName, list));
        }
        try {
            for (Executor executor : threads) {
                executor.start();
                executor.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main finished its work");
    }
}

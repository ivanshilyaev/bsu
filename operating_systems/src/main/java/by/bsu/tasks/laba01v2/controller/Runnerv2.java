package by.bsu.tasks.laba01v2.controller;

import by.bsu.tasks.laba01v2.service.FileThread;

public class Runnerv2 {
    public static void main(String[] args) {
        String regex = args[0];
        String stringToFind = args[1];
        String dirName = args[2];

        FileThread fileThread = new FileThread(regex, stringToFind, dirName);
        Thread thread = new Thread(fileThread);
        thread.start();

//        FileThread fileThread1 = new FileThread(regex, stringToFind, dirName);
//        Thread thread1 = new Thread(fileThread1);
//        thread1.start();

        // regex for filename
        // ^[\w,\s-]+\.[A-Za-z]{3}$
    }
}

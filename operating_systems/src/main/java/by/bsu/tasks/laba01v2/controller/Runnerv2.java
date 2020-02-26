package by.bsu.tasks.laba01v2.controller;

import by.bsu.tasks.laba01v2.service.FileThread;

public class Runnerv2 {
    public static void main(String[] args) {
        FileThread fileThread = new FileThread();
        fileThread.start();

        FileThread fileThread1 = new FileThread();
        fileThread1.start();
    }
}

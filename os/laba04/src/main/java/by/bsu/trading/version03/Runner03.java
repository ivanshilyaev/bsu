package by.bsu.trading.version03;


import by.bsu.trading.version03.bean.TradingData;
import by.bsu.trading.version03.threads.InputChecker;
import by.bsu.trading.version03.threads.OutputChecker;
import by.bsu.trading.version03.threads.Solver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Runner03 {
    public static Semaphore semaphore1 = new Semaphore(0);
    public static Semaphore semaphore2 = new Semaphore(0);
    public static Semaphore semaphore3 = new Semaphore(0);

    public static void main(String[] args) {
        // console variant
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter directory with tests:");
//        String dirName = scanner.nextLine();
//        File directory = new File(dirName);
//        if (!directory.isDirectory()) {
//            System.out.println("Given path is not a directory");
//            System.exit(1);
//        }
//        ArrayList<File> files = new ArrayList<>(Arrays.asList(directory.listFiles()));
//        int index = 1; // test number
//        String in;
//        String out;
//        ConsoleView view = new ConsoleView();

        // frame variant
        FrameView view = new FrameView("Trading");
        while (!view.isStarted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String dirName = view.getDirectory();
        File directory = new File(dirName);
        if (!directory.isDirectory()) {
            System.out.println("Given path is not a directory");
            System.exit(1);
        }
        ArrayList<File> files = new ArrayList<>(Arrays.asList(directory.listFiles()));
        int index = 1; // test number
        String in;
        String out;

        OutputChecker outputChecker = new OutputChecker(view);
        Solver solver = new Solver(outputChecker);
        InputChecker inputChecker = new InputChecker(solver, view);
        inputChecker.start();
        solver.start();
        outputChecker.start();

        while (!files.isEmpty()) {
            int i = 0;
            File file = files.get(i);
            while (!by.bsu.trading.version02.Helper.isInFile(file)) {
                file = files.get(++i);
            }
            in = file.getAbsolutePath();
            String inputFileName = by.bsu.trading.version02.Helper.getFileName(file);
            files.remove(i);

            i = 0;
            file = files.get(i);
            while (!by.bsu.trading.version02.Helper.getFileName(file).equals(inputFileName)) {
                file = files.get(++i);
            }
            out = file.getAbsolutePath();
            files.remove(i);

            TradingData tradingData = Helper.readFromFile(in);
            tradingData.setTestName(Helper.getFileName(file));
            tradingData.setOutputFileName(out);
            inputChecker.getQueue().add(tradingData);
            semaphore1.release();
            ++index;
        }

        try {
            inputChecker.join();
            solver.join();
            outputChecker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

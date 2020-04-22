package by.bsu.trading.version02;

import by.bsu.trading.version02.bean.TradingData;
import by.bsu.trading.version02.threads.InputChecker;
import by.bsu.trading.version02.threads.OutputChecker;
import by.bsu.trading.version02.threads.Solver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Runner02 {
    public static void main(String[] args) {
        // reading test directory and run program for every test
        System.out.println("Enter directory with tests:");
        Scanner scanner = new Scanner(System.in);
        String dirName = scanner.nextLine();
        File directory = new File(dirName);
        if (!directory.isDirectory()) {
            System.out.println("Given path is not a directory");
            System.exit(1);
        }
        ArrayList<File> files = new ArrayList<>(Arrays.asList(directory.listFiles()));
        int index = 1; // test number

        String in;
        String out;

        View view = new View();
        OutputChecker outputCheckerThread = new OutputChecker(view);
        Solver solverThread = new Solver(outputCheckerThread);
        InputChecker inputCheckerThread = new InputChecker(solverThread);

        inputCheckerThread.start();
        solverThread.start();
        outputCheckerThread.start();

        while (!files.isEmpty()) {
            int i = 0;
            File file = files.get(i);
            while (!Helper.isInFile(file)) {
                file = files.get(++i);
            }
            in = file.getAbsolutePath();
            String inputFileName = Helper.getFileName(file);
            files.remove(i);

            i = 0;
            file = files.get(i);
            while (!Helper.getFileName(file).equals(inputFileName)) {
                file = files.get(++i);
            }
            out = file.getAbsolutePath();
            files.remove(i);

            TradingData tradingData = Helper.readFromFile(in);
            tradingData.setId(index);
            tradingData.setOutputFileName(out);

            inputCheckerThread.getList().push(tradingData);
            ++index;
        }
        try {
            inputCheckerThread.setShouldBeStopped(true);
            inputCheckerThread.join();
        } catch (InterruptedException e) {
            // handling error
        }
        try {
            solverThread.setShouldBeStopped(true);
            solverThread.join();
        } catch (InterruptedException e) {
            // handling error
        }
        try {
            outputCheckerThread.setShouldBeStopped(true);
            outputCheckerThread.join();
        } catch (InterruptedException e) {
            // handling error
        }
    }
}

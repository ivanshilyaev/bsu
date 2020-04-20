package by.bsu.trading;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Runner {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    private static int m = 0;
    private static int n = 0;
    private static int k = 0;
    private static List<Double> prices = new ArrayList<>();

    private static void readFromFile(String in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(in));
        String[] array = bufferedReader.readLine().split(" ");
        m = Integer.parseInt(array[0]);
        n = Integer.parseInt(array[1]);
        k = Integer.parseInt(array[2]);
        String line = bufferedReader.readLine();
        while (line != null) {
            prices.add(Double.parseDouble(line));
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    private static List<String> solveTask(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Callable<List<String>> solver = new Solver(m, n, k, prices);
        Future<List<String>> future = executorService.submit(solver);
        return future.get();
    }

    private static void writeToFile(String out, List<String> result) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out));
        for (String line : result) {
            bufferedWriter.write(line + "\n");
        }
        bufferedWriter.close();
    }

    public static void main(String[] args) {
        try {
            ExecutorService inputCheckerExecutor = Executors.newFixedThreadPool(3);
            ExecutorService solverService = Executors.newFixedThreadPool(3);
            readFromFile(IN);

            Callable<Boolean> correctInputData = new InputChecker(m, n, k, prices);
            Future<Boolean> future = inputCheckerExecutor.submit(correctInputData);
            boolean isInputDataCorrect = future.get();

            List<String> result = new ArrayList<>();
            if (isInputDataCorrect) {
                result = solveTask(solverService);
            }
            inputCheckerExecutor.shutdown();
            solverService.shutdown();
            try {
                if (!solverService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    inputCheckerExecutor.shutdownNow();
                    solverService.shutdownNow();
                }
            } catch (InterruptedException e) {
                inputCheckerExecutor.shutdownNow();
                solverService.shutdownNow();
            }

            writeToFile(OUT, result);
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println("An error occurred");
            System.exit(1);
        }
    }
}

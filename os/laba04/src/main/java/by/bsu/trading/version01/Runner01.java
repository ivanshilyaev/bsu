package by.bsu.trading.version01;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Runner01 {
    private static String in = "/Users/ivansilaev/Desktop/tests/1.in";
    private static String out = "/Users/ivansilaev/Desktop/tests/1.out";

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

    private static boolean isInFile(File file) {
        return file.getAbsolutePath().endsWith(".in");
    }

    private static String getFileName(File file) {
        return file.getName().substring(0, file.getName().indexOf('.'));
    }

    public static void main(String[] args) {

        LinkedList<String> linkedList = new LinkedList<>();

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
        while (!files.isEmpty()) {
            int i = 0;
            File file = files.get(i);
            while (!isInFile(file)) {
                file = files.get(++i);
            }
            in = file.getAbsolutePath();
            String inputFileName = getFileName(file);
            files.remove(i);

            // running program here

            try {
                ExecutorService inputCheckerExecutor = Executors.newSingleThreadExecutor();
                ExecutorService solverService = Executors.newSingleThreadExecutor();
                ExecutorService outputCheckerExecutor = Executors.newSingleThreadExecutor();

                // reading data
                readFromFile(in);

                // checking if input data is correct
                Callable<Boolean> correctInputData = new InputChecker(m, n, k, prices);
                Future<Boolean> inputFuture = inputCheckerExecutor.submit(correctInputData);
                boolean isInputDataCorrect = inputFuture.get();

                // solving problem
                List<String> result = new ArrayList<>();
                if (isInputDataCorrect) {
                    result = solveTask(solverService);
                }

                // checking if output data is correct
                Callable<Boolean> correctOutputData = new OutputChecker(result, out);
                Future<Boolean> outputFuture = outputCheckerExecutor.submit(correctOutputData);
                boolean testPassed = outputFuture.get();
                if (testPassed) {
                    System.out.println("The test " + index++ + " is passed");
                }

                // finishing program
                inputCheckerExecutor.shutdown();
                solverService.shutdown();
                outputCheckerExecutor.shutdown();
                try {
                    if (!solverService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                        inputCheckerExecutor.shutdownNow();
                        solverService.shutdownNow();
                        outputCheckerExecutor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    inputCheckerExecutor.shutdownNow();
                    solverService.shutdownNow();
                    outputCheckerExecutor.shutdownNow();
                }

                //writeToFile(OUT, result);
            } catch (IOException | InterruptedException | ExecutionException e) {
                System.out.println("An error occurred");
                System.exit(1);
            }

            i = 0;
            file = files.get(i);
            while (!getFileName(file).equals(inputFileName)) {
                file = files.get(++i);
            }
            out = file.getAbsolutePath();
            files.remove(i);

            ++index;
        }
    }
}

package by.bsu.trading;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Runner {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    public static void main(String[] args) {
        int m = 0;
        int n = 0;
        int k = 0;
        List<Double> prices = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(IN))) {
            String[] array = bufferedReader.readLine().split(" ");
            m = Integer.parseInt(array[0]);
            n = Integer.parseInt(array[1]);
            k = Integer.parseInt(array[2]);
            String line = bufferedReader.readLine();
            while (line != null) {
                prices.add(Double.parseDouble(line));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<String> result = new ArrayList<>();
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(3);
            Callable<List<String>> solver = new Solver(m, n, k, prices);
            Future<List<String>> future = executorService.submit(solver);
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("An error occured");
            System.exit(1);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUT))) {
            for (String line : result) {
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
    }
}

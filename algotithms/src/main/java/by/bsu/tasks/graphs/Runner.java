package by.bsu.tasks.graphs;

import java.io.*;

public class Runner {
    private static final String IN = "/Users/ivansilaev/Desktop/input.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/output.txt";

    //private static final String IN = "input.txt";
    //private static final String OUT = "output.txt";

    public static void main(String[] args) {
        int n = 0;
        int array[][] = null;
        int result[] = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(IN))) {
            String line = bufferedReader.readLine();
            n = Integer.parseInt(line);
            array = new int[n][n];
            result = new int[n];
            line = bufferedReader.readLine();
            int i = 0;
            while (line != null) {
                String[] tmp = line.split(" ");
                for (int j = 0; j < n; ++j) {
                    array[i][j] = Integer.parseInt(tmp[j]);
                }
                ++i;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (array[i][j] == 1) {
                    result[j] = i + 1;
                }
            }
        }
        if (result != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUT))) {
                for (int i = 0; i < array.length - 1; ++i) {
                    bufferedWriter.write(result[i] + " ");
                }
                bufferedWriter.write(String.valueOf(result[result.length - 1]));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

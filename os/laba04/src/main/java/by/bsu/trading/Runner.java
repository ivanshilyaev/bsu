package by.bsu.trading;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    private static double countAverageValue(List<Double> list, int t, int day) {
        double average = 0;
        for (int i = day - t; i < day; ++i) {
            average += list.get(i);
        }
        return average / t;
    }

    public static void main(String[] args) {
        int m = 0;
        int n = 0;
        int k = 0;
        List<Double> list = new ArrayList<>();
        List<String> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(IN))) {
            String[] array = bufferedReader.readLine().split(" ");
            m = Integer.parseInt(array[0]);
            n = Integer.parseInt(array[1]);
            k = Integer.parseInt(array[2]);
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(Double.parseDouble(line));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<Integer> bullDays = new ArrayList<>();
        List<Integer> bearDays = new ArrayList<>();

        for (int i = n; i <= k; ++i) {
            double pn = countAverageValue(list, n, i);
            double pm = countAverageValue(list, m, i);
            if (pn < pm) {
                bullDays.add(i);
                if (i == n || bearDays.contains(i - 1)) {
                    result.add("BUY ON DAY " + i);
                }
            }
            if (pn >= pm) {
                bearDays.add(i);
                if (i == n || bullDays.contains(i - 1)) {
                    result.add("SELL ON DAY " + i);
                }
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUT))) {
            for (String line : result) {
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

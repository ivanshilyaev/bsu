package by.bsu.trading.version02;

import by.bsu.trading.version02.bean.TradingData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static synchronized TradingData readFromFile(String in) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(in))) {
            String[] array = bufferedReader.readLine().split(" ");
            int m = Integer.parseInt(array[0]);
            int n = Integer.parseInt(array[1]);
            int k = Integer.parseInt(array[2]);
            List<Double> prices = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                prices.add(Double.parseDouble(line));
                line = bufferedReader.readLine();
            }
            return new TradingData(m, n, k, prices, in);
        } catch (IOException e) {
            // error handling
            return null;
        }
    }

    public static synchronized void writeToFile(String out, List<String> signals) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out))) {
            for (String line : signals) {
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            // error handling
        }
    }

    public static synchronized boolean isInFile(File file) {
        return file.getAbsolutePath().endsWith(".in");
    }

    public static synchronized String getFileName(File file) {
        return file.getName().substring(0, file.getName().indexOf('.'));
    }

    public static synchronized String getSimpleFileName(String filename) {
        File file = new File(filename);
        return file.getName().substring(0, file.getName().indexOf('.'));
    }
}

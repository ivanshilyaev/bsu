package by.bsu.tasks.datastructures;

import java.io.*;
import java.util.LinkedList;

public class FormRunner02 {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    //private static final String IN = "in.txt";
    //private static final String OUT = "out.txt";

    static int n;
    static int m;
    static int[][] firstMatrix;
    static int[][] secondMatrix;
    static int maxValue = 0;
    static LinkedList<Integer> queue = new LinkedList<>();

    public static void processElement(int x, int y) {
        if (x < n - 1) {
            if (secondMatrix[x + 1][y] > secondMatrix[x][y]) {
                secondMatrix[x + 1][y] = Math.max(secondMatrix[x][y], firstMatrix[x + 1][y]);
                queue.add(x + 1);
                queue.add(y);
            }
        }
        if (x > 0) {
            if (secondMatrix[x - 1][y] > secondMatrix[x][y]) {
                secondMatrix[x - 1][y] = Math.max(secondMatrix[x][y], firstMatrix[x - 1][y]);
                queue.add(x - 1);
                queue.add(y);
            }
        }
        if (y > 0) {
            if (secondMatrix[x][y - 1] > secondMatrix[x][y]) {
                secondMatrix[x][y - 1] = Math.max(secondMatrix[x][y], firstMatrix[x][y - 1]);
                queue.add(x);
                queue.add(y - 1);
            }
        }
        if (y < m - 1) {
            if (secondMatrix[x][y + 1] > secondMatrix[x][y]) {
                secondMatrix[x][y + 1] = Math.max(secondMatrix[x][y], firstMatrix[x][y + 1]);
                queue.add(x);
                queue.add(y + 1);
            }
        }
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(IN))) {
            String[] array = reader.readLine().split(" ");
            n = Integer.parseInt(array[0]);
            m = Integer.parseInt(array[1]);
            firstMatrix = new int[n][m];
            secondMatrix = new int[n][m];
            for (int i = 0; i < n; ++i) {
                array = reader.readLine().split(" ");
                for (int j = 0; j < m; ++j) {
                    firstMatrix[i][j] = Integer.parseInt(array[j]);
                    if (firstMatrix[i][j] > maxValue) {
                        maxValue = firstMatrix[i][j];
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < n; ++i) {
            secondMatrix[i][0] = firstMatrix[i][0];
            secondMatrix[i][m - 1] = firstMatrix[i][m - 1];
            queue.add(i);
            queue.add(0);
            queue.add(i);
            queue.add(m - 1);
        }
        for (int j = 1; j < m - 1; ++j) {
            secondMatrix[0][j] = firstMatrix[0][j];
            secondMatrix[n - 1][j] = firstMatrix[n - 1][j];
            queue.add(0);
            queue.add(j);
            queue.add(n - 1);
            queue.add(j);
        }
        for (int i = 1; i < n - 1; ++i) {
            for (int j = 1; j < m - 1; ++j) {
                secondMatrix[i][j] = maxValue;
            }
        }
        while (!queue.isEmpty()) {
            int x = queue.poll();
            int y = queue.poll();
            processElement(x, y);
        }
        int result = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                result += (secondMatrix[i][j] - firstMatrix[i][j]);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUT))) {
            writer.write(String.valueOf(result));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

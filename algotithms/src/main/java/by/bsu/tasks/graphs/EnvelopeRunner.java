package by.bsu.tasks.graphs;

import java.io.*;

public class EnvelopeRunner {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    //private static final String IN = "in.txt";
    //private static final String OUT = "out.txt";

    static int n;
    static int result = 0;
    static Rectangle[] envelopes;
    static Rectangle[] postcards;
    static int[][] matrix;
    static int[] checkedArray;
    static int[] toArray;

    public static class Rectangle {
        double x;
        double y;

        public Rectangle(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public boolean check(Rectangle rectangle) {
            double a = Math.max(this.x, this.y);
            double b = Math.min(this.x, this.y);
            double c = Math.max(rectangle.x, rectangle.y);
            double d = Math.min(rectangle.x, rectangle.y);
            if (b > d) return false;
            if (a <= c) return true;
            if (Math.sqrt(a * a + b * b) > Math.sqrt(c * c + d * d))
                return false;
            if (a * b > c * d)
                return false;
            double ss = a * a + b * b;
            double p = Math.sqrt(ss - d * d);
            double q = Math.sqrt(ss - c * c);
            return c * d - p * q >= 2 * a * b;
        }
    }

    public static int fit(int value) {
        if (checkedArray[value] == 1) return 0;
        checkedArray[value] = 1;
        for (int i = 0; i < n; ++i) {
            if (matrix[value][i] == 1 && (toArray[i] == -1 || fit(toArray[i]) == 1)) {
                toArray[i] = value;
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(IN))) {
            n = Integer.parseInt(reader.readLine());
            envelopes = new Rectangle[n];
            postcards = new Rectangle[n];
            matrix = new int[n][n];
            checkedArray = new int[n];
            toArray = new int[n];
            String[] array;
            for (int i = 0; i < n; ++i) {
                array = reader.readLine().split(" ");
                postcards[i] = new Rectangle(Double.parseDouble(array[0]),
                        Double.parseDouble(array[1]));
            }
            for (int i = 0; i < n; ++i) {
                array = reader.readLine().split(" ");
                envelopes[i] = new Rectangle(Double.parseDouble(array[0]),
                        Double.parseDouble(array[1]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (postcards[i].check(envelopes[j])) {
                    matrix[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            toArray[i] = -1;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                checkedArray[j] = 0;
            }
            fit(i);
        }
        for (int i = 0; i < n; ++i) {
            if (toArray[i] != -1) {
                ++result;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUT))) {
            if (result == n) {
                writer.write("YES");
            } else {
                writer.write("NO\n");
                writer.write(String.valueOf(result));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

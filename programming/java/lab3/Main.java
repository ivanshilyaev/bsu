// variant 5

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void printMatrix(int[][] a, int n) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("%3d ", a[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter n -  size of square matrix: ");
        int n = in.nextInt();
        if (n <= 1) {
            do {
                System.out.print("Invalid value (require: n > 1), try again: ");
                n = in.nextInt();
            } while (n <= 1);
        }
        in.close();
        int[][] a = new int[n][n];
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = rnd.nextInt() % (n + 1);
            }
        }
        System.out.println("Source values: ");
        printMatrix(a, n);

        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                int tmp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = tmp;
            }
        }

        System.out.println();
        System.out.println("Processed values: ");
        printMatrix(a, n);
    }
}

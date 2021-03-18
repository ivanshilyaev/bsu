package com.ivanshilyaev.lll;

import java.util.Random;
import java.util.Scanner;

public class Runner {

    public static int m;
    public static int n;
    public static double delta;
    public static double[][] mu;
    public static int[][] b;
    public static int[][] bStar;

    private static void printVectors(int[][] vectors) {
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("%7d", vectors[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void startUp(Scanner scanner) {
        System.out.println("m:");
        m = scanner.nextInt();
        System.out.println("n:");
        n = scanner.nextInt();
        System.out.println("delta:");
        delta = scanner.nextDouble();
        b = new int[m][n];
        bStar = new int[m][n];
        mu = new double[m][];
    }

    public static void generateVectors() {
        System.out.println("######### b #########");
        Random random = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = random.nextInt(1999) - 999;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        startUp(scanner);
        scanner.close();
        MathService mathService = new MathService();
        LLLService lllService = new LLLService();

        int rank;
        do {
            generateVectors();
            rank = mathService.calculateRank(m, n, b);
        } while (!(rank == Math.min(n, m)));
        printVectors(b);

        System.out.println("Rank = " + rank);
        System.out.println("Vectors are linearly independent");
        System.out.println();

        lllService.gramSchmidtProcess(m, n, b, bStar, mu);
        System.out.println();

        printVectors(bStar);
        System.out.println(lllService.checkForLLL(m, n, delta, bStar, mu) ? "LLL" : "Not LLL");

        lllService.transformToLLL(m, n, delta, b, bStar, mu);
        System.out.println();

        System.out.println("mu:");
        for (int i = 1; i < mu.length; i++) {
            for (int j = 0; j < mu[i].length; j++) {
                System.out.println(mu[i][j] + " " + (Math.abs(mu[i][j]) <= 0.5 ? "+" : "-"));
            }
        }
        System.out.println();

        printVectors(b);
        System.out.println(lllService.checkForLLL(m, n, delta, bStar, mu) ? "LLL" : "Not LLL");
    }
}


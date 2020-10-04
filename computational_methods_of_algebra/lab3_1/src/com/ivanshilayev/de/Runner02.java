package com.ivanshilayev.de;

/*
 * Решение ОДУ с помощью явного метода трапеций
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Runner02 {

    private static final Double EPS_R = Math.pow(10, -6);

    // границы отрезка
    private static final int A = 0;
    private static final int B = 1;

    // порядок точности явного метода трапеций
    private static final int P = 2;

    // функция f
    private double f(double x, double y) {
        return 0.1 * x * x + 2 * x * y;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    // N - число разбиений отрезка
    private int findSolutionAndGrid() throws IOException {
        int N = 2;
        double[] xH = new double[N + 1];
        double[] yH = new double[N + 1];
        double h = (double) (B - A) / N;
        xH[0] = A;
        yH[0] = 0.8;
        for (int i = 1; i < N + 1; ++i) {
            xH[i] = A + h * i;
            yH[i] = findY(yH[i - 1], h, xH[i]);
        }

        N *= 2;
        double[] xH2 = new double[N + 1];
        double[] yH2 = new double[N + 1];
        double h2 = (double) (B - A) / N;
        xH2[0] = A;
        yH2[0] = 0.8;
        for (int i = 1; i < N + 1; ++i) {
            xH2[i] = A + h2 * i;
            yH2[i] = findY(yH2[i - 1], h2, xH2[i]);
        }

        while (findNorm(yH, yH2) / (Math.pow(2, P) - 1) >= EPS_R) {
            yH = yH2;

            N *= 2;
            xH2 = new double[N + 1];
            yH2 = new double[N + 1];
            h2 = (double) (B - A) / N;
            xH2[0] = A;
            yH2[0] = 0.8;
            for (int i = 1; i < N + 1; ++i) {
                xH2[i] = A + h2 * i;
                yH2[i] = findY(yH2[i - 1], h2, xH2[i]);
            }
        }

        // writing result to file
        FileWriter fileWriter = new FileWriter("resources/points02.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (int i = 0; i < 32; ++i) {
            // 32 points
            printWriter.println(xH2[i*32768] + " " + yH2[i*32768] + " " + (i+1));
        }
        printWriter.close();
        fileWriter.close();

        System.out.println(round(xH2[xH2.length-1], 1) + ": " + round(yH2[yH2.length - 1], 9));

        return N;
    }

    private double findNorm(double[] yH, double[] yH2) {
        double max = Math.abs(yH[0] - yH2[0]);
        for (int i = 0; i < yH.length; ++i) {
            if (Math.abs(yH[i] - yH2[2 * i]) > max) {
                max = Math.abs(yH[i] - yH2[2 * i]);
            }
        }
        return max;
    }

    private double findY(double yI, double h, double x) {
        // по методу Эйлера
        double yWave = yI + h * f(x, yI);
        return yI + 0.5 * h * (f(x, yI) + f(x, yWave));
    }

    public void start() {
        int N = 0;
        try {
            N = findSolutionAndGrid();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(N);
    }

    public static void main(String[] args) {
        Runner02 runner = new Runner02();
        runner.start();
    }
}

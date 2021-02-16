package com.ivanshilyaev.simple;

import java.io.*;

public class Runner {
    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";

    private static void executeLcg(int seed, int mult, int inc, int m, int n, int[] randomNums) throws IOException {
        Generators.lcg(seed, mult, inc, m, n, randomNums);
        Writer.writeRandomNumsToFile(FILE_NAME, n, randomNums);
        Writer.writeRandomNumsToBinFile(BIN_FILE_NAME, n, randomNums);
    }

    private static void executeLfsr(int m, int[] taps) throws IOException {
        // 8 * 2^20 + 1
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 20;
        int[] randomNums = new int[randomNumsSize];
        Generators.lfsr(m, taps, n, randomNums);
        Writer.writeRandomNumsToFile(FILE_NAME, (int) Math.pow(2, m), randomNums);
        Writer.writeRandomNumsToBinFile(BIN_FILE_NAME, (int) Math.pow(2, m), randomNums);
    }

    private static void first() throws IOException {
        int seed = 27;
        int mult = 21;
        int inc = 43;
        int m = 100;
        int n = 101;
        int[] randomNums = new int[n];
        executeLcg(seed, mult, inc, m, n, randomNums);
    }

    private static void second() throws IOException {
        int m = (int) (Math.pow(2, 20) + 14 * 10133);
        int seed = 0;
        int mult = 75;
        int inc = 3;
        int n = m + 1;
        int[] randomNums = new int[n];
        executeLcg(seed, mult, inc, m, n, randomNums);
    }

    private static void third() throws IOException {
        int m = 5;
        // x^5 + x^3 + 1
        int[] taps = {2, 4};
        executeLfsr(m, taps);
    }

    private static void fourth() throws IOException {
        int m = 10;
        // x^10 + x^7 + 1
        int[] taps = {6, 9};
        executeLfsr(m, taps);
    }

    public static void main(String[] args) throws IOException {
        // first();
        // second();
        // third();
        fourth();
    }
}

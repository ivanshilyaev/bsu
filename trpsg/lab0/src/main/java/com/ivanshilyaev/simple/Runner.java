package com.ivanshilyaev.simple;

import java.io.*;

public class Runner {
    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";
    private static final String PI_DIGITS_FILE_NAME = "src/main/resources/piDigits.txt";

    private static void executeLcg(int seed, int mult, int inc, int m, int n, int[] randomNums) throws IOException {
        Generators.lcg(seed, mult, inc, m, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, n, randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, n, randomNums);
    }

    private static void executeLfsr(int m, int[] taps) throws IOException {
        // 8 * 2^20 + 1
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.lfsr(m, taps, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, (int) Math.pow(2, m), randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, (int) Math.pow(2, m), randomNums);
    }

    private static void executeShrinkingGenerator(int m1, int[] taps1, int m2, int[] taps2) throws IOException {
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.shrinkingGenerator(m1, taps1, m2, taps2, n, randomNums);
        int period = 66;
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, period + 1, randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, period + 1, randomNums);
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

    private static void fifth() throws IOException {
        int m1 = 5;
        int[] taps1 = {2, 4};
        int m2 = 10;
        int[] taps2 = {6, 9};
        executeShrinkingGenerator(m1, taps1, m2, taps2);
    }

    private static void sixth() throws IOException {
        int n = (int) Math.pow(2, 18);
        int[] nums = new int[n];
        new PiDigitsReader().execute(PI_DIGITS_FILE_NAME, n, nums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, 65536, nums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, 65536, nums);
    }

    public static void main(String[] args) throws IOException {
        // first();
        // second();
        // third();
        // fourth();
        // fifth();
        sixth();
    }
}

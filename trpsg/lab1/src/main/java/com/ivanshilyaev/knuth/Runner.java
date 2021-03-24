package com.ivanshilyaev.knuth;

import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Arrays;

public class Runner {

    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";
    private static final String OTHER_BIN_FILE_NAME = "src/main/resources/result2.bin";
    private static final String TEST = "src/main/resources/phys.bin";

    private static final double SIGNIFICANCE_LEVEL = 0.05;

    private static void runLcg() throws Exception {
        int m = (int) (Math.pow(2, 20) + 14 * 10133);
        int seed = 0;
        int mult = 1;
        int inc = 3;
        int n = m + 1;
        int[] randomNums = new int[n];
        Generators.lcg(seed, mult, inc, m, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, n, randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
    }

    private static void executeLfsr(int m, int[] taps) throws Exception {
        // 8 * 2^20 + 1
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.lfsr(m, taps, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, (int) Math.pow(2, m), randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
    }

    private static void executeShrinkingGenerator(int m1, int[] taps1, int m2, int[] taps2) throws Exception {
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.shrinkingGenerator(m1, taps1, m2, taps2, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, randomNumsSize, randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
    }

    private static double q(int d, int r) {
        double sum = 0;
        for (int j = 0; j <= d; ++j) {
            sum += Math.pow(-1, d + j) * CombinatoricsUtils.binomialCoefficientDouble(d, j)
                * Math.pow((double) j / d, r);
        }
        return 1 - sum;
    }

    private static double chiSquaredTest(int[] count, int d, int t) {
        long[] observed = new long[t - d + 1];
        for (int i = d; i <= t; ++i) {
            observed[i - d] = count[i - d];
        }
        double[] expected = new double[t - d + 1];
        for (int i = d; i < t; ++i) {
            expected[i - d] = Math.abs(q(d, i - 1) - q(d, i));
        }
        expected[t - d] = Math.abs(q(d, t - 1));
        ChiSquareTest chiSquareTest = new ChiSquareTest();
        double pValue = chiSquareTest.chiSquareTest(expected, observed);
        //System.out.println("pValue = " + pValue);
        return pValue;
    }

    /**
     * @param d - 0 <= y[i] < d
     * @param t - d <= r < t
     * @param n - количество отрезков, собравших все купоны
     */
    private static double couponCollectorsTest(byte[] y, int d, int t, int n) {
        int j = -1;
        // текущее количество отрезков, собравших все купоны
        int s = 0;
        int countR = 0;
        int countT = 0;
        boolean[] occurs;
        int[] count = new int[t + 1];
        while (s < n) {
            occurs = new boolean[d];
            // количество различных значений в наблюдаемой последовательности
            int q = 0;
            // длина текущего отрезка
            int r = 0;
            while (q < d) {
                do {
                    ++r;
                    ++j;
                } while (occurs[(y[j] + 128) % d]);
                occurs[(y[j] + 128) % d] = true;
                ++q;
            }
            if (r > t) {
                ++countT;
            } else if (r == t) {
                ++count[t];
                ++countT;
            } else {
                ++countR;
                ++count[r];
            }
            ++s;
        }
        //System.out.println("Количетсво отрезков с длиной меньше t: " + countR);
        //System.out.println("Количетсво отрезков с длиной t и более: " + countT);
        return chiSquaredTest(count, d, t);
    }

    /**
     * @param q - количество тестируемых подпоследовательностей
     */
    private static boolean testOnSubsequences(byte[] y, int d, int t, int n, int q) {
        double[] pValues = new double[q];
        for (int i = 0; i < q; ++i) {
            byte[] copyY = new byte[y.length / q];
            for (int j = 0; j < copyY.length; ++j) {
                copyY[j] = y[j * q + i];
            }
            pValues[i] = couponCollectorsTest(copyY, d, t, n);
        }
        long[] observed = new long[100];
        Arrays.fill(observed, q / 100);
        double[] expected = new double[100];
        Arrays.fill(expected, 0.01);
        for (double pValue : pValues) {
            if ((int) Math.floor(pValue * 100) == 100) {
                ++expected[99];
            } else {
                ++expected[(int) Math.floor(pValue * 100)];
            }
        }
        ChiSquareTest chiSquareTest = new ChiSquareTest();
        double pValue = chiSquareTest.chiSquareTest(expected, observed);
        System.out.println("pValue = " + pValue);
        return pValue > SIGNIFICANCE_LEVEL;
    }

    private static void additionalTask() throws Exception {
        int m1 = 10;
        // x^10 + x^7 + 1
        int[] taps1 = {6, 9};
        executeLfsr(m1, taps1);
        byte[] y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### РСЛОС степени 10 #########");
        double pValue = couponCollectorsTest(y, 64, 1000, 10);
        System.out.println("pValue = " + pValue);
        boolean answer;
        if (Double.isNaN(pValue)) {
            answer = true;
        } else {
            answer = pValue > SIGNIFICANCE_LEVEL;
        }
        System.out.println("Принимаем последовательность? " + answer);
        System.out.println();

        int m2 = 20;
        // x^20 + x^17 + 1
        int[] taps2 = {16, 19};
        executeLfsr(m2, taps2);
        y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### РСЛОС степени 20 #########");
        pValue = couponCollectorsTest(y, 64, 1000, 10);
        System.out.println("pValue = " + pValue);
        if (Double.isNaN(pValue)) {
            answer = true;
        } else {
            answer = pValue > SIGNIFICANCE_LEVEL;
        }
        System.out.println("Принимаем последовательность? " + answer);
        System.out.println();

        executeShrinkingGenerator(m1, taps1, m2, taps2);
        y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### Самосжимающийся генератор #########");
        pValue = couponCollectorsTest(y, 64, 1000, 10);
        System.out.println("pValue = " + pValue);
        if (Double.isNaN(pValue)) {
            answer = true;
        } else {
            answer = pValue > SIGNIFICANCE_LEVEL;
        }
        System.out.println("Принимаем последовательность? " + answer);
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        // 1
        // runLcg();
        byte[] y = BinFileReader.readBytes(TEST);
        System.out.println("######### Критерий собирания купонов #########");
        double pValue = couponCollectorsTest(y, 64, 250, 100);
        System.out.println("pValue = " + pValue);
        System.out.println("Принимаем последовательность? " +
            (pValue > SIGNIFICANCE_LEVEL));
        System.out.println();

        // 2
        System.out.println("######### Критерий подпоследовательностей #########");
        System.out.println("Принимаем последовательность? " +
            testOnSubsequences(y, 64, 250, 1, 100));
        System.out.println();

        // 3
        additionalTask();
    }
}

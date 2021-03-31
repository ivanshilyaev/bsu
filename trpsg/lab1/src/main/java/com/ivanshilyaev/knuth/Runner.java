package com.ivanshilyaev.knuth;

import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.io.IOException;
import java.util.Arrays;

public class Runner {

    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String FILE_NAME2 = "src/main/resources/result2.txt";
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

    private static int[] executeLfsr(int m, int[] taps) throws Exception {
        // 8 * 2^20 + 1
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.lfsr(m, taps, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, (int) Math.pow(2, m), randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
        return randomNums;
    }

    private static int[] executeShrinkingGenerator(int m1, int[] taps1, int m2, int[] taps2) throws Exception {
        int n = (int) (Math.pow(2, 23) + 1);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.shrinkingGenerator(m1, taps1, m2, taps2, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, randomNumsSize, randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
        return randomNums;
    }

    private static double q(int d, int r) {
        double sum = 0;
        for (int j = 0; j <= d; ++j) {
            sum += Math.pow(-1, d + j) * CombinatoricsUtils.binomialCoefficientDouble(d, j)
                * Math.pow((double) j / d, r);
        }
        return 1 - sum;
    }

    private static double chiSquaredTest(int[] count, int d, int t) throws IOException {
        long[] observed = new long[t - d + 1];
        for (int i = d; i <= t; ++i) {
            observed[i - d] = count[i - d];
        }
        double[] expected = new double[t - d + 1];
        for (int i = d; i < t; ++i) {
            expected[i - d] = Math.abs(q(d, i - 1) - q(d, i));
        }
        //SequenceWriter.writeDoubleRandomNumsToFile(FILE_NAME, expected);
        //SequenceWriter.writeDoubleRandomNumsToFile(FILE_NAME2, observed);
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
    private static double couponCollectorsTest(byte[] y, int d, int t, int n) throws IOException {
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
    private static double testOnSubsequences(byte[] y, int d, int q) throws IOException {
        double[] pValues = new double[q];
        for (int i = 0; i < q; ++i) {
            byte[] copyY = new byte[y.length / q];
            for (int j = 0; j < copyY.length; ++j) {
                copyY[j] = y[j * q + i];
            }
            pValues[i] = seriesCriterion(copyY, d, 128);
        }
        double[] expected = new double[100];
        Arrays.fill(expected, 0.01);
        long[] observed = new long[100];
        for (double pValue : pValues) {
            if ((int) Math.floor(pValue * 100) == 100) {
                ++observed[99];
            } else {
                ++observed[(int) Math.floor(pValue * 100)];
            }
        }

        System.out.println(Arrays.toString(observed));

        ChiSquareTest chiSquareTest = new ChiSquareTest();
        return chiSquareTest.chiSquareTest(expected, observed);
    }

    private static void additionalTask() throws Exception {
//        int m1 = 10;
//        // x^10 + x^7 + 1
//        int[] taps1 = {6, 9};
//        int[] tempY = executeLfsr(m1, taps1);
//        byte[] y = new byte[tempY.length];
//        for (int i = 0; i < y.length; ++i) {
//            y[i] = (byte) tempY[i];
//        }
//        //byte[] y = BinFileReader.readBytes(BIN_FILE_NAME);
//        System.out.println("######### РСЛОС степени 10 #########");
//        double pValue = testOnSubsequences(y, 256, 100);
//        System.out.println("pValue = " + pValue);
//        boolean answer = pValue > SIGNIFICANCE_LEVEL;
//        System.out.println("Принимаем последовательность? " + answer);
//        System.out.println();

        int m2 = 20;
        // x^20 + x^17 + 1
        int[] taps2 = {16, 19};
        int[] tempY = executeLfsr(m2, taps2);
        byte[] y = new byte[tempY.length];
        for (int i = 0; i < y.length; ++i) {
            y[i] = (byte) tempY[i];
        }
        //y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### РСЛОС степени 20 #########");
        double pValue = testOnSubsequences(y, 256, 1000);
        System.out.println("pValue = " + pValue);
        boolean answer = pValue > SIGNIFICANCE_LEVEL;
        System.out.println("Принимаем последовательность? " + answer);
        System.out.println();

//        tempY  = executeShrinkingGenerator(m1, taps1, m2, taps2);
//        for (int i = 0; i < y.length; ++i) {
//            y[i] = (byte) tempY[i];
//        }
//        //y = BinFileReader.readBytes(BIN_FILE_NAME);
//        System.out.println("######### Самосжимающийся генератор #########");
//        pValue = seriesCriterion(y, 256, 128);
//        System.out.println("pValue = " + pValue);
//        answer = pValue > SIGNIFICANCE_LEVEL;
//        System.out.println("Принимаем последовательность? " + answer);
//        System.out.println();
    }

    // ----------------- ANOTHER CRITERIA BEGIN -----------------

    public static double seriesCriterion(byte[] y, int d, int shift) throws IOException {
        int[][] counts = new int[d][d];
        for (int i = 0; i < ((y.length - 2) / 2); ++i) {
            ++counts[y[2 * i] + shift][y[2 * i + 1] + shift];
        }
        ++counts[y[y.length - 2] + shift][y[y.length - 1] + shift];

        long[] observed = new long[d * d];
        int k = 0;
        for (int i = 0; i < d; ++i) {
            for (int j = 0; j < d; ++j) {
                observed[k] = counts[i][j];
                ++k;
            }
        }

        //System.out.println(Arrays.toString(observed));

        double[] expected = new double[d * d];
        Arrays.fill(expected, 1 / Math.pow(d, 2));

        ChiSquareTest chiSquareTest = new ChiSquareTest();
        return chiSquareTest.chiSquareTest(expected, observed);
    }

    // ----------------- ANOTHER CRITERIA END -----------------

    public static void main(String[] args) throws Exception {
        // 1
        // runLcg();
//        byte[] y = BinFileReader.readBytes(TEST);
//        System.out.println("######### Критерий собирания купонов #########");
//        double pValue = couponCollectorsTest(y, 64, 250, 100);
//        System.out.println("pValue = " + pValue);
//        System.out.println("Принимаем последовательность? " +
//            (pValue > SIGNIFICANCE_LEVEL));
//        System.out.println();
//
//        // 2
//        System.out.println("######### Критерий подпоследовательностей #########");
//        pValue = testOnSubsequences(y, 64, 250, 1, 100);
//        boolean answer;
//        if (Double.isNaN(pValue)) {
//            answer = true;
//        } else {
//            answer = pValue > SIGNIFICANCE_LEVEL;
//        }
//        System.out.println("Принимаем последовательность? " + answer);
//        System.out.println();
//
//        // 3
//        additionalTask();


        byte[] y = BinFileReader.readBytes(TEST);
        // 1
        System.out.println("######### Критерий серий #########");
        double pValue = seriesCriterion(y, 256, 128);
        System.out.println("pValue = " + pValue);
        System.out.println(pValue > SIGNIFICANCE_LEVEL);
        System.out.println();

        // 2
        System.out.println("######### Критерий подпоследовательностей #########");
        pValue = testOnSubsequences(y, 256, 100);
        System.out.println("pValue = " + pValue);
        System.out.println(pValue > SIGNIFICANCE_LEVEL);
        System.out.println();

        // 3
        additionalTask();
    }
}

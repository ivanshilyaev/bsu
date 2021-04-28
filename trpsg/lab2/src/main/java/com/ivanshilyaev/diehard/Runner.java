package com.ivanshilyaev.diehard;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import java.util.Arrays;

import static com.ivanshilyaev.diehard.Utils.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Runner {

    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";
    private static final String TEST = "src/main/resources/phys.bin";

    private static final double SIGNIFICANCE_LEVEL = 0.05;

    private static int[] executeLfsr(int m, int[] taps) throws Exception {
        int n = (int) pow(2, 20);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.lfsr(m, taps, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, (int) pow(2, m), randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
        return randomNums;
    }

    private static void theOverlappingSumsTest(int[] nums) {


        KolmogorovSmirnovTest kolmogorovSmirnovTest = new KolmogorovSmirnovTest();
    }

    private static boolean theCrapsTest(int[] nums) {
        int numsIndex = 0;
        int n = 200000;
        double p = 244.0 / 495.0;
        double mean = n * p;
        long sigma = (long) sqrt(mean * (1 - p));

        double[] expected = new double[21];
        long[] observed = new long[21];
        double sum = 1.0 / 3.0;
        expected[0] = sum;
        for (int i = 1; i < 20; ++i) {
            expected[i] = (27.0*pow(27.0/36.0,i-1) + 40*pow(13.0/18.0,i-1) +
                55.0*pow(25.0/36.0,i-1))/648.0;
            sum += expected[i];
        }
        expected[20] = 1 - sum;
        for (int i = 0; i < 21; ++i) {
            expected[i] *= n;
        }

        Arrays.fill(observed, 0);
        int wins = 0;
        for (int i = 0; i < n; ++i) {
            int point = roll(nums[numsIndex++]) + roll(nums[numsIndex++]);
            int tries = 0;
            if (point == 7 || point == 11) {
                ++wins;
                ++observed[tries];
            }
            else if (point == 2 || point == 3 || point == 12) {
                ++observed[tries];
            }
            else {
                while (true) {
                    if (tries < 20) {
                        ++tries;
                    }
                    int aThrow = roll(nums[numsIndex++]) + roll(nums[numsIndex++]);
                    if (aThrow == 7) {
                        ++observed[tries];
                        break;
                    } else if (aThrow == point) {
                        ++wins;
                        ++observed[tries];
                        break;
                    }
                }
            }
        }
        ++wins;

        System.out.println(wins);
        KolmogorovSmirnovTest kolmogorovSmirnovTest = new KolmogorovSmirnovTest();
        double firstPValue = kolmogorovSmirnovTest.kolmogorovSmirnovTest(new NormalDistribution(mean, sigma), new double[]{wins, wins});
        ChiSquareTest chiSquareTest = new ChiSquareTest();
        double secondPValue = chiSquareTest.chiSquareTest(expected, observed);
        System.out.println("first p-value: " + firstPValue);
        System.out.println("second p-value: " + secondPValue);
        return (firstPValue > SIGNIFICANCE_LEVEL && secondPValue > SIGNIFICANCE_LEVEL);
    }

    public static void main(String[] args) throws Exception {
        // LFSR 10
//        int m = 10;
//        int[] taps = {6, 9};
//        int[] nums = executeLfsr(m, taps);
//        System.out.println(theCrapsTest(nums));

        // Test file
        byte[] y = BinFileReader.readBytes(TEST);
        int[] nums = new int[y.length];
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = y[i] + 128;
        }
        System.out.println(theCrapsTest(nums));
    }
}

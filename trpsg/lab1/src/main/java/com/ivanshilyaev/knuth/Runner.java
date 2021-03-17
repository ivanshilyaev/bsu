package com.ivanshilyaev.knuth;

public class Runner {

    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";
    private static final String OTHER_BIN_FILE_NAME = "src/main/resources/result2.bin";

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

    /**
     * @param d - 0 <= y[i] < d
     * @param t - d <= r < t
     * @param n - количество отрезков, собравших все купоны
     */
    private static boolean couponCollectorsTest(byte[] y, int d, int t, int n, int shift) {
        int j = -1;
        // текущее количество отрезков, собравших все купоны
        int s = 0;
        int countR = 0;
        int countT = 0;
        boolean[] occurs;
        while (s < n) {
            occurs = new boolean[d];
            // количество различных значений в наблюдаемой последовательности
            int q = 0;
            // длина текущего отрезка
            int r = 0;
            while (q < d) {
                do {
                    ++r;
                    j += shift;
                } while (occurs[(y[j] + 128) % d]);
                occurs[(y[j] + 128) % d] = true;
                ++q;
            }
            if (r >= t) ++countT;
            else ++countR;
            ++s;
        }
        System.out.println("Количетсво отрезков с длиной меньше t: " + countR);
        System.out.println("Количетсво отрезков с длиной t и более: " + countT);
        if (countR == 0) {
            return true;
        } else {
            return countT / countR > 19;
        }
    }

    /**
     * @param q - количество тестируемых подпоследовательностей
     */
    private static boolean testOnSubsequences(byte[] y, int d, int t, int n, int q) {
        boolean result = true;
        for (int i = 0; i < q; ++i) {
            byte[] copyY = new byte[y.length / (i + 1)];
            for (int j = 0; j < copyY.length; ++j) {
                copyY[j] = y[(i + 1) * j];
            }
            if (!couponCollectorsTest(copyY, d, t, n, i + 1)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static void additionalTask() throws Exception {
        int m1 = 10;
        // x^10 + x^7 + 1
        int[] taps1 = {6, 9};
        executeLfsr(m1, taps1);
        byte[] y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### РСЛОС степени 10 #########");
        System.out.println("Принимаем последовательность? " +
            couponCollectorsTest(y, 256, 1500, 100, 1));
        System.out.println();

        int m2 = 20;
        // x^20 + x^17 + 1
        int[] taps2 = {16, 19};
        executeLfsr(m2, taps2);
        y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### РСЛОС степени 20 #########");
        System.out.println("Принимаем последовательность? " +
            couponCollectorsTest(y, 256, 1500, 100, 1));
        System.out.println();

        executeShrinkingGenerator(m1, taps1, m2, taps2);
        y = BinFileReader.readBytes(BIN_FILE_NAME);
        System.out.println("######### Самосжимающийся генератор #########");
        System.out.println("Принимаем последовательность? " +
            couponCollectorsTest(y, 256, 1500, 100, 1));
    }

    public static void main(String[] args) throws Exception {
        // 1
        // runLcg();
        byte[] y = BinFileReader.readBytes(OTHER_BIN_FILE_NAME);
        System.out.println("######### Критерий собирания купонов #########");
        System.out.println("Принимаем последовательность? " +
            couponCollectorsTest(y, 256, 1500, 100, 1));
        System.out.println();

        // 2
        System.out.println("######### Критерий подпоследовательностей #########");
        System.out.println("Принимаем последовательность? " +
            testOnSubsequences(y, 256, 1500, 100, 3));
        System.out.println();

        // 3
        additionalTask();
    }
}

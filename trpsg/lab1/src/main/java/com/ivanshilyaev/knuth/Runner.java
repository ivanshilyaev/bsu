package com.ivanshilyaev.knuth;

import java.io.IOException;

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

    /**
     * @param d - 0 <= y[i] < d
     * @param t - d <= r < t
     * @param n - количество отрезков, собравших все купоны
     */
    private static boolean couponCollectorsTest(int d, int t, int n) throws IOException {
        byte[] y = BinFileReader.readBytes(OTHER_BIN_FILE_NAME);
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
                    ++j;
                } while (occurs[y[j] + 128]);
                occurs[y[j] + 128] = true;
                ++q;
            }
            System.out.println(r);
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

    public static void main(String[] args) throws Exception {
        // runLcg();
        System.out.println(couponCollectorsTest(256, 1500, 100));
    }
}

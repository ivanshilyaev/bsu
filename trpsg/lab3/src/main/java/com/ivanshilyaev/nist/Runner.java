package com.ivanshilyaev.nist;

import java.io.IOException;
import java.util.BitSet;

import static java.lang.Math.E;
import static java.lang.Math.pow;

public class Runner {

    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";
    private static final String TEST = "src/main/resources/phys.bin";

    private static final double SIGNIFICANCE_LEVEL = 0.01;

    private static int[] executeLfsr(int m, int[] taps) throws Exception {
        int n = (int) pow(2, 23);
        int randomNumsSize = n / 8;
        int[] randomNums = new int[randomNumsSize];
        Generators.lfsr(m, taps, n, randomNums);
        SequenceWriter.writeRandomNumsToFile(FILE_NAME, randomNumsSize, randomNums);
        SequenceWriter.writeRandomNumsToBinFile(BIN_FILE_NAME, randomNums);
        return randomNums;
    }

    private static boolean binaryMatrixRankTest(byte[] y, int n, int M, int Q) {
        int N = n / (M * Q);
        int fullRank = 0; // F_M
        int almostFullRank = 0; // F_{M-1}
        int remaining = 0;
        // Разбиваем биты на матрицы и для каждой матрицы подсчитываем ранг
        int[][] matrix = new int[M][Q];
        BitSet bitSet = BitSet.valueOf(y);
        int index = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                for (int k = 0; k < Q; ++k) {
                    if (bitSet.get(index++)) {
                        matrix[j][k] = 1;
                    }
                    else {
                        matrix[j][k] = 0;
                    }
                }
            }
            int rank = Utils.calculateRank(M, Q, matrix);
            if (rank == M) {
                ++fullRank;
            }
            else if (rank == M - 1) {
                ++almostFullRank;
            }
            else {
                ++remaining;
            }
        }
        // Вычисляем X^2 и p-value
        double chiSquare = pow(fullRank - 0.2888 * N, 2) / (0.2888 * N) +
            pow(almostFullRank - 0.5776 * N, 2) / (0.5776 * N) +
            pow(remaining - 0.1336 * N, 2) / (0.1336 * N);
        double pValue = pow(E, chiSquare / -2);
        System.out.println(chiSquare);
        System.out.println(N);
        System.out.println(fullRank);
        System.out.println(almostFullRank);
        System.out.println("p-value = " + pValue);
        return pValue >= SIGNIFICANCE_LEVEL;
    }

    public static void main(String[] args) throws IOException {
        byte[] y = BinFileReader.readBytes(TEST);
        y = new byte[]{89, 42};
        System.out.println(binaryMatrixRankTest(y, 20, 3, 3));
    }
}

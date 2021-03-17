package com.ivanshilyaev.knuth;

public class Generators {

    public static void lcg(int seed, int mult, int inc, int m, int n, int[] randomNums) {
        randomNums[0] = seed;
        for (int i = 1; i < n; ++i) {
            randomNums[i] = (mult * randomNums[i - 1] + inc) % m;
        }
    }

    public static void lfsr(int m, int[] taps, int n, int[] randomNums) {
        // seed = 1
        boolean[] bits = new boolean[m];
        bits[m - 1] = true;
        for (int i = 0; i < n - 8; i += 8) {
            StringBuilder builder = new StringBuilder();
            for (int k = 0; k < 8; ++k) {
                boolean bit = nextBitLFSR(m, bits, taps);
                if (bit) {
                    builder.append("1");
                } else {
                    builder.append("0");
                }
            }
            int number = Integer.parseInt(builder.toString(), 2);
            randomNums[i / 8] = number;
        }
    }

    public static void shrinkingGenerator(
        int m1, int[] taps1, int m2, int[] taps2,
        int n, int[] randomNums
    ) {
        boolean[] bits1 = new boolean[m1];
        bits1[m1 - 1] = true;
        boolean[] bits2 = new boolean[m2];
        bits2[m2 - 2] = true;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n - 8; i += 8) {
            for (int k = 0; k < 8; ++k) {
                boolean bit1 = nextBitLFSR(m1, bits1, taps1);
                boolean bit2 = nextBitLFSR(m2, bits2, taps2);
                if (bit1) {
                    if (bit2) {
                        builder.append("1");
                    } else {
                        builder.append("0");
                    }
                }
            }
        }
        for (int i = 0; i < builder.length() - 8; i += 8) {
            int number = Integer.parseInt(builder.substring(i, i + 8), 2);
            randomNums[i / 8] = number;
        }
    }

    private static boolean nextBitLFSR(int m, boolean[] bits, int[] taps) {
        // 1. read next output bit from bits[0]
        boolean bit = bits[0];
        // 2. count new bit
        boolean newBit = bits[(m - 1) - taps[0]];
        for (int j = 1; j < taps.length; ++j) {
            boolean tempBit = bits[(m - 1) - taps[j]];
            // xor
            newBit = (!newBit & tempBit) | (newBit & !tempBit);
        }
        // 3. shift
        for (int j = 0; j < m - 1; ++j) {
            bits[j] = bits[j + 1];
        }
        // 4. write new bit to bits[m-1]
        bits[m - 1] = newBit;
        return bit;
    }
}

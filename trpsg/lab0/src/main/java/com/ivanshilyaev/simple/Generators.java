package com.ivanshilyaev.simple;

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
        for (int i = 0; i < n - 20; i += 20) {
            StringBuilder builder = new StringBuilder();
            for (int k = 0; k < 20; ++k) {
                // 1. read next output bit from bits[m-1]
                boolean bit = bits[0];
                if (bit) {
                    builder.append("1");
                } else {
                    builder.append("0");
                }
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
                // 4. write new bit to bits[0]
                bits[m - 1] = newBit;
            }
            int number = Integer.parseInt(builder.toString(), 2);
            randomNums[i / 20] = number;
        }
    }
}

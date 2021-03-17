package com.ivanshilyaev.knuth;

public class Generators {

    public static void lcg(int seed, int mult, int inc, int m, int n, int[] randomNums) {
        randomNums[0] = seed;
        for (int i = 1; i < n; ++i) {
            randomNums[i] = (mult * randomNums[i - 1] + inc) % m;
        }
    }
}

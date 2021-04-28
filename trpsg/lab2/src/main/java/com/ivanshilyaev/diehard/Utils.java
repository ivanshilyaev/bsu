package com.ivanshilyaev.diehard;

public class Utils {

    private static final int MAX = 256;

    public static int roll(int num) {
        return 1 + 6 * num / MAX;
    }
}

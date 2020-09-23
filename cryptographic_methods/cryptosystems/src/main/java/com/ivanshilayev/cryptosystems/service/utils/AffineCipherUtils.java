package com.ivanshilayev.cryptosystems.service.utils;

import com.ivanshilayev.cryptosystems.service.exception.CipherException;

public class AffineCipherUtils {

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // euler function
    private static int phi(int m) {
        int result = 1;
        for (int i = 2; i < m; ++i) {
            if (gcd(m, i) == 1) {
                ++result;
            }
        }
        return result;
    }

    public static int findModularMultiplicativeInverse(int a, int m) throws CipherException {
        if (gcd(a, m) != 1) {
            throw new CipherException("Modular multiplicative inverse (mod " + m + ") doesn't exist for number " + a);
        }
        int power = phi(m) - 1;
        int aInverse = a;
        for (int i = 1; i < power; ++i) {
            aInverse = (aInverse * a) % m;
        }
        return aInverse;
    }
}

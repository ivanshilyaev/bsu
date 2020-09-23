package com.ivanshilayev.cryptosystems.service.utils;

import com.ivanshilayev.cryptosystems.service.exception.CipherException;

public class HillCipherUtils {

    public static int[][] findInverseMatrixTwoByTwo(int[][] key, int m) throws CipherException {
        int det = Math.abs(key[0][0] * key[1][1] - key[0][1] * key[1][0]);
        int detReverse = CommonUtils.findModularMultiplicativeInverse(det, m);
        int[][] result = new int[2][2];
        result[0][0] = detReverse * key[1][1] % m;
        result[1][1] = detReverse * key[0][0] % m;
        result[0][1] = -detReverse * key[0][1] % m;
        result[1][0] = -detReverse * key[1][0] % m;
        return result;
    }

    public static int[] multiplyVectorByMatrixModulo(int[] x, int[][] key, int m) throws CipherException {
        if (x.length != key.length) {
            throw new CipherException("Couldn't multiply vector by matrix (incompatible size): " +
                    x.length + " and " + key.length + "x" + key[0].length);
        }
        int[] result = new int[x.length];
        for (int j = 0; j < key[0].length; ++j) {
            for (int i = 0; i < x.length; ++i) {
                result[j] += x[i] * key[i][j];
            }
            result[j] = Math.abs(result[j]) % m;
        }
        return result;
    }
}

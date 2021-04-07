package com.ivanshilyaev.rsa.service;

import com.ivanshilyaev.rsa.exception.RsaException;

import java.math.BigInteger;

public class RsaUtils {

    public BigInteger extendedEuclideanAlgorithm(BigInteger a, BigInteger b) throws RsaException {
        if (a.compareTo(b) < 0) {
            throw new RsaException("a is less then b");
        }
        BigInteger r0 = a;
        BigInteger r1 = b;
        BigInteger r2;
        BigInteger x0 = BigInteger.ONE;
        BigInteger x1 = BigInteger.ZERO;
        BigInteger x2;
        BigInteger y0 = BigInteger.ZERO;
        BigInteger y1 = BigInteger.ONE;
        BigInteger y2;
        BigInteger q;
        while (!r1.equals(BigInteger.ZERO)) {
            // (2)
            q = r0.divide(r1);
            r2 = r0.subtract(q.multiply(r1));
            r0 = r1;
            r1 = r2;
            // (3)
            x2 = x0.subtract(q.multiply(x1));
            x0 = x1;
            x1 = x2;
            // (4)
            y2 = y0.subtract(q.multiply(y1));
            y0 = y1;
            y1 = y2;
        }
        if (!r0.equals(BigInteger.ONE)) {
            throw new RsaException("No multiplicative inverse to a mod b");
        }
        if (y0.compareTo(BigInteger.ZERO) < 0) {
            return y0.add(a);
        }
        return y0;
    }
}

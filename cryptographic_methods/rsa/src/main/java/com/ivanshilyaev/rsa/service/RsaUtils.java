package com.ivanshilyaev.rsa.service;

import com.ivanshilyaev.rsa.exception.RsaException;

import java.math.BigInteger;
import java.util.Random;

import static com.ivanshilyaev.rsa.service.RsaConstants.*;

public class RsaUtils {

    public BigInteger extendedEuclideanAlgorithm(BigInteger a, BigInteger b) throws RsaException {
        if (a.compareTo(b) < 0) {
            throw new RsaException("a is less then b");
        }
        BigInteger r0 = a;
        BigInteger r1 = b;
        BigInteger r2;
        BigInteger x0 = ONE;
        BigInteger x1 = ZERO;
        BigInteger x2;
        BigInteger y0 = ZERO;
        BigInteger y1 = ONE;
        BigInteger y2;
        BigInteger q;
        while (!r1.equals(ZERO)) {
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
        if (!r0.equals(ONE)) {
            throw new RsaException("No multiplicative inverse to a mod b");
        }
        if (y0.compareTo(ZERO) < 0) {
            return y0.add(a);
        }
        return y0;
    }

    public BigInteger modPow(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger u = ONE;
        BigInteger v = a;
        String binaryB = b.toString(2);
        for (int i = binaryB.length() - 1; i >= 0; --i) {
            if (binaryB.charAt(i) == '1') {
                u = u.multiply(v).mod(n);
            }
            v = v.multiply(v).mod(n);
        }
        return u;
    }

    public BigInteger genPrimeJava(int k) {
        return BigInteger.probablePrime(k, new Random());
    }

    public BigInteger genPrime(int k) {
        return BigInteger.TWO;
    }

    public boolean fermatPrimalityTest(BigInteger n) {
        for (int i = 0; i < ROUNDS; ++i) {
            BigInteger a = new BigInteger(n.toString(2).length() - 1, new Random());
            if (!a.gcd(n).equals(ONE)) {
                return false;
            }
            if (!modPow(a, n.subtract(ONE), n).equals(ONE)) {
                return false;
            }
        }
        return true;
    }
}

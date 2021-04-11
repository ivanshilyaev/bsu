package com.ivanshilyaev.rsa.service;

import com.ivanshilyaev.rsa.exception.RsaException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.ivanshilyaev.rsa.service.RsaConstants.*;

public class RsaImpl {

    private final RsaUtils rsaUtils = new RsaUtils();

    public List<BigInteger> generate(int l) throws RsaException {
        BigInteger e = new BigInteger("65537");
        BigInteger p = rsaUtils.genPrimeJava(l / 2);
        while (!e.gcd(p.subtract(ONE)).equals(ONE) || !rsaUtils.fermatPrimalityTest(p)) {
            p = rsaUtils.genPrimeJava(l / 2);
        }
        BigInteger q = rsaUtils.genPrimeJava(l / 2);
        while (!e.gcd(q.subtract(ONE)).equals(ONE) || !rsaUtils.fermatPrimalityTest(q)) {
            q = rsaUtils.genPrimeJava(l / 2);
        }
        BigInteger n = p.multiply(q);
        BigInteger phiN = p.subtract(ONE).multiply(q.subtract(ONE));
        BigInteger d = rsaUtils.extendedEuclideanAlgorithm(phiN, e);
        List<BigInteger> list = new ArrayList<>();
        list.add(e);
        list.add(n);
        list.add(d);
        return list;
    }

    public BigInteger encrypt(BigInteger n, BigInteger e, BigInteger x) {
        return rsaUtils.modPow(x, e, n);
    }

    public BigInteger decrypt(BigInteger n, BigInteger d, BigInteger y) {
        return rsaUtils.modPow(y, d, n);
    }
}

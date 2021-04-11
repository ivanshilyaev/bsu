package com.ivanshilyaev.rsa.service;

import com.ivanshilyaev.rsa.exception.RsaException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
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

    public String encryptText(BigInteger n, BigInteger e, String text) {
        BigInteger x = new BigInteger(text.getBytes());
        BigInteger y = rsaUtils.modPow(x, e, n);
        return Base64.getEncoder().encodeToString(y.toByteArray());
    }

    public String decryptText(BigInteger n, BigInteger d, String text) {
        BigInteger y = new BigInteger(Base64.getDecoder().decode(text));
        BigInteger x = rsaUtils.modPow(y, d, n);
        return new String(x.toByteArray());
    }

}

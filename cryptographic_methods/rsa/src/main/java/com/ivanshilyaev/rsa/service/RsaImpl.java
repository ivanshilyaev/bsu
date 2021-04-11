package com.ivanshilyaev.rsa.service;

import com.ivanshilyaev.rsa.exception.RsaException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.ivanshilyaev.rsa.service.RsaConstants.*;

public class RsaImpl {

    private final RsaUtils rsaUtils = new RsaUtils();
    private String privateKey;
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

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

        publicKey = Base64.getEncoder()
            .encodeToString(("(" + n + ",\n" + e + ")").getBytes());
        privateKey = Base64.getEncoder()
            .encodeToString(("(" + n + ",\n" + d + ")").getBytes());

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

    public BigInteger encrypt(BigInteger x) {
        String[] array = new String(Base64.getDecoder().decode(publicKey)).split("\n");
        BigInteger n = new BigInteger(array[0].substring(1, array[0].length() - 1));
        BigInteger e = new BigInteger(array[1].substring(0, array[1].length() - 1));
        return rsaUtils.modPow(x, e, n);
    }

    public BigInteger decrypt(BigInteger y) {
        String[] array = new String(Base64.getDecoder().decode(privateKey)).split("\n");
        BigInteger n = new BigInteger(array[0].substring(1, array[0].length() - 1));
        BigInteger d = new BigInteger(array[1].substring(0, array[1].length() - 1));
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

    public String encryptText(String text) {
       return encryptText(text, publicKey);
    }

    public String decryptText(String text) {
        BigInteger y = new BigInteger(Base64.getDecoder().decode(text));
        String[] array = new String(Base64.getDecoder().decode(privateKey)).split("\n");
        BigInteger n = new BigInteger(array[0].substring(1, array[0].length() - 1));
        BigInteger d = new BigInteger(array[1].substring(0, array[1].length() - 1));
        BigInteger x = rsaUtils.modPow(y, d, n);
        return new String(x.toByteArray());
    }

    public String encryptText(String text, String publicKey) {
        String[] array = new String(Base64.getDecoder().decode(publicKey)).split("\n");
        BigInteger n = new BigInteger(array[0].substring(1, array[0].length() - 1));
        BigInteger e = new BigInteger(array[1].substring(0, array[1].length() - 1));
        BigInteger x = new BigInteger(text.getBytes());
        BigInteger y = rsaUtils.modPow(x, e, n);
        return Base64.getEncoder().encodeToString(y.toByteArray());
    }
}

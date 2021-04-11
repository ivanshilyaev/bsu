package com.ivanshilyaev.rsa.runner;

import com.ivanshilyaev.rsa.exception.RsaException;
import com.ivanshilyaev.rsa.service.RsaImpl;

import java.math.BigInteger;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        try {
            int l = 2048;
            RsaImpl rsaImpl = new RsaImpl();
            List<BigInteger> list = rsaImpl.generate(l);
            BigInteger e = list.get(0);
            BigInteger n = list.get(1);
            BigInteger d = list.get(2);

            System.out.println("e = " + e);
            System.out.println("n = " + n);
            System.out.println("d = " + d);

            BigInteger x = new BigInteger("123");
            BigInteger y = rsaImpl.encrypt(x);
            x = rsaImpl.decrypt(y);
            System.out.println("x = " + x);
            System.out.println("y = " + y);

            System.out.println("\nText encryption/decryption:");
            String textX = "Hello, World!";
            String textY = rsaImpl.encryptText(textX);
            textX = rsaImpl.decryptText(textY);
            System.out.println("textX = " + textX);
            System.out.println("textY = " + textY);
            System.out.println("public key = " + rsaImpl.getPublicKey());
        } catch (RsaException rsaException) {
            System.out.println(rsaException.getMessage());
        }
    }
}

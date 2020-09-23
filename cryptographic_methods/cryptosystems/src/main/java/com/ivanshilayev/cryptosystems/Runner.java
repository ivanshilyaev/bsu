package com.ivanshilayev.cryptosystems;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.impl.CaesarCipher;

public class Runner {
    public static void main(String[] args) {
        Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        CaesarCipher caesarCipher = new CaesarCipher();
        String text = "ABCD";
        String key = "B";
        String encrypted = caesarCipher.encrypt(alphabet, text, key);
        String decrypted = caesarCipher.decrypt(alphabet, encrypted, key);
        System.out.println(encrypted);
        System.out.println(decrypted);
    }
}

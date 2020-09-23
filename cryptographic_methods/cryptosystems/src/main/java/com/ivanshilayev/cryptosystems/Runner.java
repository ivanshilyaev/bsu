package com.ivanshilayev.cryptosystems;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.impl.CaesarCipher;

public class Runner {
    public static void main(String[] args) {
        Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        CaesarCipher caesarCipher = new CaesarCipher();
        System.out.println(caesarCipher.encrypt(alphabet, "ABCD", "B"));
    }
}

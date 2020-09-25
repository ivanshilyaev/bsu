package com.ivanshilayev.cryptosystems;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.Cipher;
import com.ivanshilayev.cryptosystems.service.exception.CipherException;
import com.ivanshilayev.cryptosystems.service.impl.HillCipher;
import com.ivanshilayev.cryptosystems.service.impl.TranspositionCipher;
import com.ivanshilayev.cryptosystems.service.impl.VigenereCipher;

public class Runner {
    public static void main(String[] args) {
        try {
            Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            Cipher cipher = new VigenereCipher();
            String text = "HELLOWORLD";
            String key = "KEY";
            String encrypted = cipher.encrypt(alphabet, text, key);
            String decrypted = cipher.decrypt(alphabet, encrypted, key);
            System.out.println("Encrypted text: " + encrypted);
            System.out.println("Decrypted text: " + decrypted);
        } catch (CipherException e) {
            System.out.println(e.getMessage());
        }
    }
}

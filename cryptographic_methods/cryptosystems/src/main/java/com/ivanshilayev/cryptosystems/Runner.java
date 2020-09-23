package com.ivanshilayev.cryptosystems;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.Cipher;
import com.ivanshilayev.cryptosystems.service.exception.CipherException;
import com.ivanshilayev.cryptosystems.service.impl.AffineCipher;
import com.ivanshilayev.cryptosystems.service.impl.CaesarCipher;
import com.ivanshilayev.cryptosystems.service.impl.HillCipher;
import com.ivanshilayev.cryptosystems.service.impl.SimpleSubstitutionCipher;
import com.ivanshilayev.cryptosystems.service.utils.AffineCipherUtils;

public class Runner {
    public static void main(String[] args) {
        try {
            Alphabet alphabet = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            Cipher cipher = new HillCipher();
            String text = "ABCD";
            String key = "DDCF";
            String encrypted = cipher.encrypt(alphabet, text, key);
            String decrypted = cipher.decrypt(alphabet, encrypted, key);
            System.out.println("Encrypted text: " + encrypted);
            System.out.println("Decrypted text: " + decrypted);
        } catch (CipherException e) {
            System.out.println(e.getMessage());
        }
    }
}

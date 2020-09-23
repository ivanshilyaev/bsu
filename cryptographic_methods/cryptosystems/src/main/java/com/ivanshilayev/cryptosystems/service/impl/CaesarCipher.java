package com.ivanshilayev.cryptosystems.service.impl;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.Cipher;

public class CaesarCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) {
        int shift = alphabet.getSymbols().indexOf(key.charAt(0));
        StringBuilder encryptedText = new StringBuilder();
        int index;
        for (char c : text.toCharArray()) {
            index = (alphabet.getSymbols().indexOf(c) + shift) % alphabet.getSize();
            encryptedText.append(alphabet.getSymbols().get(index));
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) {
        int shift = alphabet.getSymbols().indexOf(key.charAt(0));
        StringBuilder decryptedText = new StringBuilder();
        int index;
        for (char c : text.toCharArray()) {
            index = (alphabet.getSymbols().indexOf(c) - shift) % alphabet.getSize();
            decryptedText.append(alphabet.getSymbols().get(index));
        }
        return decryptedText.toString();
    }
}

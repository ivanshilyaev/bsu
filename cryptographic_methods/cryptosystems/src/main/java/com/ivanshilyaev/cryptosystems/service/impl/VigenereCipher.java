package com.ivanshilyaev.cryptosystems.service.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;

public class VigenereCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) throws CipherException {
        StringBuilder encryptedText = new StringBuilder();
        int index;
        int shift;
        for (int i = 0; i < text.length(); ++i) {
            shift = alphabet.getSymbols().indexOf(key.charAt(i % key.length()));
            index = (alphabet.getSymbols().indexOf(text.charAt(i)) + shift) % alphabet.getSize();
            encryptedText.append(alphabet.getSymbols().get(index));
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) throws CipherException {
        StringBuilder decryptedText = new StringBuilder();
        int index;
        int shift;
        for (int i = 0; i < text.length(); ++i) {
            shift = alphabet.getSymbols().indexOf(key.charAt(i % key.length()));
            index = (alphabet.getSize() + alphabet.getSymbols().indexOf(text.charAt(i)) - shift) % alphabet.getSize();
            decryptedText.append(alphabet.getSymbols().get(index));
        }
        return decryptedText.toString();
    }
}

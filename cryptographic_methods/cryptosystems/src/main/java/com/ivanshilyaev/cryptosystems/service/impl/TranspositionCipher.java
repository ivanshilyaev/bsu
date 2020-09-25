package com.ivanshilyaev.cryptosystems.service.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;

import java.util.List;
import java.util.stream.Collectors;

public class TranspositionCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) throws CipherException {
        while (text.length() % key.length() != 0) {
            text += alphabet.getSymbols().get(0);
        }
        List<Character> keyChars = key.chars().mapToObj(e -> (char) e).sorted().collect(Collectors.toList());
        int[] transposition = new int[key.length()];
        for (int i = 0; i < key.length(); ++i) {
            transposition[i] = keyChars.indexOf(key.charAt(i));
        }
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += key.length()) {
            for (int j = 0; j < key.length(); ++j) {
                for (int k = 0; k < key.length(); ++k) {
                    if (transposition[k] == j) {
                        encryptedText.append(text.charAt(i + k));
                        break;
                    }
                }
            }
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) throws CipherException {
        List<Character> keyChars = key.chars().mapToObj(e -> (char) e).sorted().collect(Collectors.toList());
        int[] transposition = new int[key.length()];
        for (int i = 0; i < key.length(); ++i) {
            transposition[i] = keyChars.indexOf(key.charAt(i));
        }
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += key.length()) {
            for (int j = 0; j < key.length(); ++j) {
                decryptedText.append(text.charAt(i + transposition[j]));
            }
        }
        return decryptedText.toString();
    }
}

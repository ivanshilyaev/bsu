package com.ivanshilayev.cryptosystems.service.impl;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.Cipher;
import com.ivanshilayev.cryptosystems.service.exception.CipherException;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleSubstitutionCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) throws CipherException {
        List<Character> substitution = key.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        StringBuilder encryptedText = new StringBuilder();
        int index;
        for (char c : text.toCharArray()) {
            index = (alphabet.getSymbols().indexOf(c));
            encryptedText.append(substitution.get(index));
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) throws CipherException {
        List<Character> substitution = key.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        StringBuilder decryptedText = new StringBuilder();
        int index;
        for (char c : text.toCharArray()) {
            index = (substitution.indexOf(c));
            decryptedText.append(alphabet.getSymbols().get(index));
        }
        return decryptedText.toString();
    }
}

package com.ivanshilayev.cryptosystems.service.impl;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.Cipher;
import com.ivanshilayev.cryptosystems.service.exception.CipherException;
import com.ivanshilayev.cryptosystems.service.utils.AffineCipherUtils;

public class AffineCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) {
        int a = alphabet.getSymbols().indexOf(key.charAt(0));
        int b = alphabet.getSymbols().indexOf(key.charAt(1));
        StringBuilder encryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = (a * alphabet.getSymbols().indexOf(c) + b) % alphabet.getSize();
            encryptedText.append(alphabet.getSymbols().get(index));
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) throws CipherException {
        int a = alphabet.getSymbols().indexOf(key.charAt(0));
        int b = alphabet.getSymbols().indexOf(key.charAt(1));
        int aInverse = AffineCipherUtils.findModularMultiplicativeInverse(a, alphabet.getSize());
        StringBuilder decryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = (aInverse * (alphabet.getSymbols().indexOf(c) - b)) % alphabet.getSize();
            decryptedText.append(alphabet.getSymbols().get(index));
        }
        return decryptedText.toString();
    }
}

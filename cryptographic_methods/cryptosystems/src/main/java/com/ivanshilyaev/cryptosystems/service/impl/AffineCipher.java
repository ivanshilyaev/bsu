package com.ivanshilyaev.cryptosystems.service.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.Cipher;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;
import com.ivanshilyaev.cryptosystems.utils.CommonUtils;

public class AffineCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) {
        int a = alphabet.getSymbols().indexOf(key.charAt(0));
        int b = alphabet.getSymbols().indexOf(key.charAt(1));
        StringBuilder encryptedText = new StringBuilder();
        int index;
        for (char c : text.toCharArray()) {
            index = (a * alphabet.getSymbols().indexOf(c) + b) % alphabet.getSize();
            encryptedText.append(alphabet.getSymbols().get(index));
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) throws CipherException {
        int a = alphabet.getSymbols().indexOf(key.charAt(0));
        int b = alphabet.getSymbols().indexOf(key.charAt(1));
        int aInverse = CommonUtils.findModularMultiplicativeInverse(a, alphabet.getSize());
        StringBuilder decryptedText = new StringBuilder();
        int index;
        for (char c : text.toCharArray()) {
            index = (aInverse * (alphabet.getSymbols().indexOf(c) - b)) % alphabet.getSize();
            decryptedText.append(alphabet.getSymbols().get(index));
        }
        return decryptedText.toString();
    }
}

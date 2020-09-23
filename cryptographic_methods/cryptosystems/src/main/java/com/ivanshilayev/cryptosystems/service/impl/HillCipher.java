package com.ivanshilayev.cryptosystems.service.impl;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.Cipher;
import com.ivanshilayev.cryptosystems.service.exception.CipherException;
import com.ivanshilayev.cryptosystems.service.utils.HillCipherUtils;

public class HillCipher implements Cipher {
    @Override
    public String encrypt(Alphabet alphabet, String text, String key) throws CipherException {
        if (text.length() % 2 == 1) {
            text += alphabet.getSymbols().get(0);
        }
        int[][] keyMatrix = new int[2][2];
        keyMatrix[0][0] = alphabet.getSymbols().indexOf(key.charAt(0));
        keyMatrix[0][1] = alphabet.getSymbols().indexOf(key.charAt(1));
        keyMatrix[1][0] = alphabet.getSymbols().indexOf(key.charAt(2));
        keyMatrix[1][1] = alphabet.getSymbols().indexOf(key.charAt(3));
        StringBuilder encryptedText = new StringBuilder();
        int[] x;
        for (int i = 0; i < text.length(); i += 2) {
            x = new int[2];
            x[0] = alphabet.getSymbols().indexOf(text.charAt(i));
            x[1] = alphabet.getSymbols().indexOf(text.charAt(i + 1));
            int[] encryptedVector = HillCipherUtils.multiplyVectorByMatrixModulo(x, keyMatrix, alphabet.getSize());
            encryptedText.append(alphabet.getSymbols().get(encryptedVector[0]));
            encryptedText.append(alphabet.getSymbols().get(encryptedVector[1]));
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(Alphabet alphabet, String text, String key) throws CipherException {
        int[][] keyMatrix = new int[2][2];
        keyMatrix[0][0] = alphabet.getSymbols().indexOf(key.charAt(0));
        keyMatrix[0][1] = alphabet.getSymbols().indexOf(key.charAt(1));
        keyMatrix[1][0] = alphabet.getSymbols().indexOf(key.charAt(2));
        keyMatrix[1][1] = alphabet.getSymbols().indexOf(key.charAt(3));
        int[][] keyMatrixInverse = HillCipherUtils.findInverseMatrixTwoByTwo(keyMatrix, alphabet.getSize());
        StringBuilder decryptedText = new StringBuilder();
        int[] y;
        for (int i = 0; i < text.length(); i += 2) {
            y = new int[2];
            y[0] = alphabet.getSymbols().indexOf(text.charAt(i));
            y[1] = alphabet.getSymbols().indexOf(text.charAt(i + 1));
            int[] decryptedVector = HillCipherUtils.multiplyVectorByMatrixModulo(y, keyMatrixInverse, alphabet.getSize());
            decryptedText.append(alphabet.getSymbols().get(decryptedVector[0]));
            decryptedText.append(alphabet.getSymbols().get(decryptedVector[1]));
        }
        return decryptedText.toString();
    }
}

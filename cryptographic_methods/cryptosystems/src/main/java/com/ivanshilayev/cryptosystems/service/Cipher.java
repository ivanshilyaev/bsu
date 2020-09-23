package com.ivanshilayev.cryptosystems.service;

import com.ivanshilayev.cryptosystems.model.Alphabet;
import com.ivanshilayev.cryptosystems.service.exception.CipherException;

public interface Cipher {
    String encrypt(Alphabet alphabet, String text, String key) throws CipherException;

    String decrypt(Alphabet alphabet, String text, String key) throws CipherException;
}

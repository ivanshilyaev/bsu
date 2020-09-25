package com.ivanshilyaev.cryptosystems.service;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.service.exception.CipherException;

public interface Cipher {
    String encrypt(Alphabet alphabet, String text, String key) throws CipherException;

    String decrypt(Alphabet alphabet, String text, String key) throws CipherException;
}

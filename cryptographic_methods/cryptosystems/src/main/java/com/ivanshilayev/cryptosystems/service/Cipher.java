package com.ivanshilayev.cryptosystems.service;

import com.ivanshilayev.cryptosystems.model.Alphabet;

public interface Cipher {
    String encrypt(Alphabet alphabet, String text, String key);

    String decrypt(Alphabet alphabet, String text, String key);
}

package com.ivanshilyaev.cryptosystems.validator;

public class ValidationConstants {
    public static final String ALPHABET_ERROR = "Error! Alphabet must not contain duplicate characters.";
    public static final String TEXT_ERROR = "Error! Text symbols are not presented in the alphabet.";
    public static final String KEY_STORE_ERROR = "Error! Key symbols are not presented in the alphabet.";
    public static final String AFFINE_CIPHER_KEY_ERROR = "Error! (k1, M) != 1.";
    public static final String SIMPLE_SUBSTITUTION_CIPHER_KEY_ERROR = "Error! Alphabet and key don't have the same length.";
    public static final String SIMPLE_SUBSTITUTION_CIPHER_KEY_ERROR_2 = "Error! Each symbol of the key should be presented in the alphabet only once.";
    public static final String HILL_CIPHER_KEY_ERROR = "Error! Key length should be 4.";
    public static final String HILL_CIPHER_KEY_ERROR_2 = "Error! Det shouldn't be zero.";
    public static final String HILL_CIPHER_KEY_ERROR_3 = "Error! (det, M) != 1.";
    public static final String TRANSPOSITION_CIPHER_KEY_ERROR = "Error! Key is to long.";
    public static final String TRANSPOSITION_CIPHER_KEY_ERROR_2 = "Error! Key must not contain duplicate characters.";
}

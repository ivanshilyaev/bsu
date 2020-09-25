package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class TranspositionCipherKeyValidator implements Validator {
    private Alphabet alphabet;

    public TranspositionCipherKeyValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        if (line.length() > alphabet.getSize()) {
            throw new ValidationException(ValidationConstants.TRANSPOSITION_CIPHER_KEY_ERROR);
        }
        for (int i = 0; i < line.length(); ++i) {
            if (!alphabet.getSymbols().contains(line.charAt(i))) {
                throw new ValidationException(ValidationConstants.KEY_STORE_ERROR);
            }
        }
        String temp;
        for (int i = 0; i < line.length() - 1; ++i) {
            temp = line.substring(i + 1);
            if (temp.contains("" + line.charAt(i))) {
                throw new ValidationException(ValidationConstants.TRANSPOSITION_CIPHER_KEY_ERROR_2);
            }
        }
    }
}

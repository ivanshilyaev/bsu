package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class CaesarCipherKeyValidator implements Validator {
    private Alphabet alphabet;

    public CaesarCipherKeyValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        if (!alphabet.getSymbols().contains(line.charAt(0))) {
            throw new ValidationException(ValidationConstants.KEY_STORE_ERROR);
        }
    }
}

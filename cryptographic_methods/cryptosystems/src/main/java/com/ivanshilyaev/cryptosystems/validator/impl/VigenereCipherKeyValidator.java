package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class VigenereCipherKeyValidator implements Validator {
    private Alphabet alphabet;

    public VigenereCipherKeyValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        for (int i = 0; i < line.length(); ++i) {
            if (!alphabet.getSymbols().contains(line.charAt(i))) {
                throw new ValidationException(ValidationConstants.KEY_STORE_ERROR);
            }
        }
    }
}

package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class TextValidator implements Validator {
    private Alphabet alphabet;

    public TextValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        for (char c : line.toCharArray()) {
            if (!alphabet.getSymbols().contains(c)) {
                throw new ValidationException(ValidationConstants.TEXT_ERROR);
            }
        }
    }
}

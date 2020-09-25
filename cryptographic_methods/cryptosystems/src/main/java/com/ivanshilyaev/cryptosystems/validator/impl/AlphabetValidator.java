package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class AlphabetValidator implements Validator {
    @Override
    public void validate(String line) throws ValidationException {
        String temp;
        for (int i = 0; i < line.length() - 1; ++i) {
            temp = line.substring(i + 1);
            if (temp.contains("" + line.charAt(i))) {
                throw new ValidationException(ValidationConstants.ALPHABET_ERROR);
            }
        }
    }
}

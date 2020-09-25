package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class SimpleSubstitutionCipherKeyValidator implements Validator {
    private Alphabet alphabet;

    public SimpleSubstitutionCipherKeyValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        List<Character> copy = new ArrayList<>(alphabet.getSymbols());
        if (copy.size() != line.length()) {
            throw new ValidationException(ValidationConstants.SIMPLE_SUBSTITUTION_CIPHER_KEY_ERROR);
        }
        for (int i = 0; i < line.length(); ++i) {
            if (!copy.contains(line.charAt(i))) {
                throw new ValidationException(ValidationConstants.KEY_STORE_ERROR);
            }
        }
        for (Character character : copy) {
            int length = line.length();
            line = line.replaceAll(character.toString(), "");
            if (line.length() != length - 1) {
                throw new ValidationException(ValidationConstants.SIMPLE_SUBSTITUTION_CIPHER_KEY_ERROR_2);
            }
        }
    }
}

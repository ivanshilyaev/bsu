package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.utils.CommonUtils;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class AffineCipheKeyValidator implements Validator {
    private Alphabet alphabet;

    public AffineCipheKeyValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        if (!alphabet.getSymbols().contains(line.charAt(0)) ||
                !alphabet.getSymbols().contains(line.charAt(1))) {
            throw new ValidationException(ValidationConstants.KEY_STORE_ERROR);
        }
        int a = alphabet.getSymbols().indexOf(line.charAt(0));
        if (CommonUtils.gcd(a, alphabet.getSize()) != 1) {
            throw new ValidationException(ValidationConstants.AFFINE_CIPHER_KEY_ERROR);
        }
    }
}

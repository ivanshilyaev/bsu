package com.ivanshilyaev.cryptosystems.validator.impl;

import com.ivanshilyaev.cryptosystems.model.Alphabet;
import com.ivanshilyaev.cryptosystems.utils.CommonUtils;
import com.ivanshilyaev.cryptosystems.validator.ValidationConstants;
import com.ivanshilyaev.cryptosystems.validator.Validator;
import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public class HillCipherKeyValidator implements Validator {
    private Alphabet alphabet;

    public HillCipherKeyValidator(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(String line) throws ValidationException {
        if (line.length() != 4) {
            throw new ValidationException(ValidationConstants.HILL_CIPHER_KEY_ERROR);
        }
        for (int i = 0; i < line.length(); ++i) {
            if (!alphabet.getSymbols().contains(line.charAt(i))) {
                throw new ValidationException(ValidationConstants.KEY_STORE_ERROR);
            }
        }
        int[][] keyMatrix = new int[2][2];
        keyMatrix[0][0] = alphabet.getSymbols().indexOf(line.charAt(0));
        keyMatrix[0][1] = alphabet.getSymbols().indexOf(line.charAt(1));
        keyMatrix[1][0] = alphabet.getSymbols().indexOf(line.charAt(2));
        keyMatrix[1][1] = alphabet.getSymbols().indexOf(line.charAt(3));
        int det = Math.abs(keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]);
        if (det == 0) {
            throw new ValidationException(ValidationConstants.HILL_CIPHER_KEY_ERROR_2);
        }
        if (CommonUtils.gcd(det, alphabet.getSize()) != 1) {
            throw new ValidationException(ValidationConstants.HILL_CIPHER_KEY_ERROR_3);
        }
    }
}

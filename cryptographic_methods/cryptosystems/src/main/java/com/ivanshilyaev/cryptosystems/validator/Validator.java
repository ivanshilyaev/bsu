package com.ivanshilyaev.cryptosystems.validator;

import com.ivanshilyaev.cryptosystems.validator.exception.ValidationException;

public interface Validator {
    void validate(String line) throws ValidationException;
}

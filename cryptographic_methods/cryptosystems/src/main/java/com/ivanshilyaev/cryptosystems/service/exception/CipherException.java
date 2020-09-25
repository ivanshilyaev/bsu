package com.ivanshilyaev.cryptosystems.service.exception;

public class CipherException extends Exception {
    public CipherException() {
    }

    public CipherException(String message) {
        super(message);
    }

    public CipherException(String message, Throwable cause) {
        super(message, cause);
    }

    public CipherException(Throwable cause) {
        super(cause);
    }
}

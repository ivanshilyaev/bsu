package com.ivanshilyaev.rsa.exception;

public class RsaException extends Exception {

    public RsaException() {
    }

    public RsaException(String message) {
        super(message);
    }

    public RsaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RsaException(Throwable cause) {
        super(cause);
    }
}

package com.koala.core.exception;

/**
 * koala的非捕获异常
 */
public class BaseKoalaRuntimeException extends RuntimeException {

    public BaseKoalaRuntimeException() {
    }

    public BaseKoalaRuntimeException(String message) {
        super(message);
    }

    public BaseKoalaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseKoalaRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseKoalaRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

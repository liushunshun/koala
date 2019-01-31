package com.koala.core.exception;

/**
 * koala的基础异常
 */
public class BaseKoalaException extends Exception {

    public BaseKoalaException() {
    }

    public BaseKoalaException(String message) {
        super(message);
    }

    public BaseKoalaException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseKoalaException(Throwable cause) {
        super(cause);
    }

    public BaseKoalaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

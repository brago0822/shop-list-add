package com.brago.app.shoplistapp.exception;

import lombok.Getter;

@Getter
public class ShopListApiException extends RuntimeException {

    String code;
    String name;
    String message;

    public ShopListApiException(ErrorType errorType, String message) {
        super(message);
        this.code = errorType.code;
        this.message = message;
        this.name = errorType.name;
    }

    public ShopListApiException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.code = errorType.code;
        this.message = message;
        this.name = errorType.name;
    }

    private static String toString(ErrorType errorType) {
        return String.format("[%s: %s] %s", errorType.code, errorType.name, errorType.message);
    }
}

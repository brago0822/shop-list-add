package com.brago.app.shoplistapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ShopListApiException {

    String resource;
    String id;
    ErrorType errorType;

    public ResourceNotFoundException(ErrorType errorType, String resource, String id) {
        super(errorType, getErrorMessage(errorType, resource, id));
        this.errorType = errorType;
        this.resource = resource;
        this.id = id;
    }

    private static String getErrorMessage(ErrorType errorType, String resource, String id) {
        return String.format(errorType.message, resource, id);
    }
}

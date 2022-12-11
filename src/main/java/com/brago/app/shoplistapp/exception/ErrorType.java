package com.brago.app.shoplistapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorType {

    RESOURCE_NOT_FOUND_ERROR ("1001","Resource not found", "Resource %s not found with id %s");

    String code;
    String name;
    String message;

}

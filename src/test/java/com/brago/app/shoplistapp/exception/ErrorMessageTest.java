package com.brago.app.shoplistapp.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorMessageTest {

    @BeforeEach
    void setUp() {}

    @Test
    void testBuilder() {
        // Setup
        final ErrorMessage expectedResult = new ErrorMessage("123", "Message 123", "This is a message", "/v1/path");
        // Run the test
        final ErrorMessage result = ErrorMessage.builder()
                .code("123")
                .name("Message 123")
                .message("This is a message")
                .path("/v1/path")
                .build();

        // Verify the results
        assertEquals(expectedResult.getCode(), result.getCode());
        assertEquals(expectedResult.getName(), result.getName());
        assertEquals(expectedResult.getMessage(), result.getMessage());
        assertEquals(expectedResult.getPath(), result.getPath());
    }
}

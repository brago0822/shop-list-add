package com.brago.app.shoplistapp.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler controllerExceptionHandlerUnderTest;

    @BeforeEach
    void setUp() {
        controllerExceptionHandlerUnderTest = new ControllerExceptionHandler();
    }

    @Test
    void testResourceNotFoundException() {
        // Setup
        final ResourceNotFoundException ex = new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR,
                "PRODUCTS LIST", "1");

        MockHttpServletRequest mockServletRequest = new MockHttpServletRequest();
        ServletWebRequest request = new ServletWebRequest(mockServletRequest);
        mockServletRequest.setRequestURI("/products");

        final ErrorMessage  expectedResult = new ErrorMessage(
                "1001",
                "Resource not found",
                "Resource PRODUCTS LIST not found with id 1",
                "/products"
        );

        // Run the test
        final ErrorMessage result = controllerExceptionHandlerUnderTest.resourceNotFoundException(ex, request);

        // Verify the results
        assertEquals(expectedResult.getCode(), result.getCode());
        assertEquals(expectedResult.getName(), result.getName());
        assertEquals(expectedResult.getMessage(), result.getMessage());
        assertEquals(expectedResult.getPath(), result.getPath());
    }

    /*@Test
    void testHandleMethodArgumentNotValid() {
        // Setup
       *//* MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = new BindException(new ShoppingList(), "shoppingList");
        bindingResult.addError(new ObjectError("name", "should not be empty"));
       *//**//* when(bindingResult.getFieldErrors())
                .thenReturn(Arrays.asList(new FieldError("shoppingList", "name", "should not be empty")));*//**//*
//        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        doReturn(bindingResult).when(methodArgumentNotValidException).getBindingResult();*//*

        BindingResult bindingResult = new BeanPropertyBindingResult(new ShoppingList(), "shoppingList");
        bindingResult.addError(new ObjectError("name", "should not be empty"));
//        errors.add(new FieldError("shoppingList", "name", "should not be empty"))
//        bindingResult.addAllErrors(Arrays.asList(new FieldError("shoppingList", "name", "should not be empty"));
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
//        BindingResult bindingResult = mock(BindingResult.class);
        *//*when(bindingResult.getFieldErrors())
                .thenReturn(Arrays.asList(new FieldError("shoppingList", "name", "should not be empty")));*//*
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
//        doReturn(bindingResult).when(methodArgumentNotValidException).getBindingResult();
        final HttpHeaders headers = new HttpHeaders();

        final HttpStatusCode status = HttpStatusCode.valueOf(400);

        MockHttpServletRequest mockServletRequest = new MockHttpServletRequest();
        ServletWebRequest request = new ServletWebRequest(mockServletRequest);
        mockServletRequest.setRequestURI("/products");

        final ErrorMessage  expectedResult = new ErrorMessage(
                "400",
                "Bad request",
                "Request method 'GET' is not supported",
                "/products"
        );

        // Run the test
        final ResponseEntity<Object> result = controllerExceptionHandlerUnderTest.handleMethodArgumentNotValid(
                methodArgumentNotValidException,
                headers,
                status,
                request);

        // Verify the results
        ErrorMessage resultErrorMessage = (ErrorMessage) result.getBody();
        assertEquals(expectedResult.getCode(), resultErrorMessage.getCode());
        assertEquals(expectedResult.getName(), resultErrorMessage.getName());
        assertEquals(expectedResult.getMessage(), resultErrorMessage.getMessage());
        assertEquals(expectedResult.getPath(), resultErrorMessage.getPath());
    }*/

    @Test
    void testHandleHttpRequestMethodNotSupported() {
        // Setup
        final HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("GET",
                List.of("POST"));

        final HttpHeaders headers = new HttpHeaders();
        headers.add("test", "test");
        final HttpStatusCode status = HttpStatusCode.valueOf(500);
        MockHttpServletRequest mockServletRequest = new MockHttpServletRequest();
        ServletWebRequest request = new ServletWebRequest(mockServletRequest);
        mockServletRequest.setRequestURI("/products");

        final ErrorMessage  expectedResult = new ErrorMessage(
                "SL500",
                "Internal Server Error",
                "Request method 'GET' is not supported",
                "/products"
        );

        // Run the test
        final ResponseEntity<Object> result = controllerExceptionHandlerUnderTest.handleHttpRequestMethodNotSupported(
                ex, headers, status, request);

        // Verify the results
        ErrorMessage resultErrorMessage = (ErrorMessage) result.getBody();
        assertEquals(expectedResult.getCode(), resultErrorMessage.getCode());
        assertEquals(expectedResult.getName(), resultErrorMessage.getName());
        assertEquals(expectedResult.getMessage(), resultErrorMessage.getMessage());
        assertEquals(expectedResult.getPath(), resultErrorMessage.getPath());

    }

    @Test
    void testGeneralException() {
        // Setup
        MockHttpServletRequest mockServletRequest = new MockHttpServletRequest();
        ServletWebRequest request = new ServletWebRequest(mockServletRequest);
        mockServletRequest.setRequestURI("/products");

        final ErrorMessage  expectedResult = new ErrorMessage("Exception nos controlled test");
        expectedResult.setPath("/products");

        // Run the test
        final ErrorMessage result = controllerExceptionHandlerUnderTest.generalException(new Exception("Exception nos controlled test"),
                request);
        // Verify the results
        assertEquals(expectedResult.getMessage(), result.getMessage());
        assertEquals(expectedResult.getPath(), result.getPath());
    }
}

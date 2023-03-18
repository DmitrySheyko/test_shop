package com.example.test_shop.exceptions;

/**
 * Class of custom {@link ValidationException}
 *
 * @author Dmitry Sheyko
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

}

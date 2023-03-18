package com.example.test_shop.exceptions;

/**
 * Class of custom {@link NotFoundException}
 *
 * @author Dmitry Sheyko
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}

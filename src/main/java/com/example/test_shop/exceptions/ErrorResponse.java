package com.example.test_shop.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Class of entity {@link ErrorResponse}.
 * This returns by ErrorHandlers placed in class {@link ErrorHandler} in the case of exception.
 *
 * @author Dmitry Sheyko
 */
@Getter
@Setter
@Builder
public class ErrorResponse {

    private String message;
    private String reason;
    private String status;
    private LocalDateTime dateTime;

    public ErrorResponse(String message, String reason, String status, LocalDateTime dateTime) {
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.dateTime = dateTime;
    }

}

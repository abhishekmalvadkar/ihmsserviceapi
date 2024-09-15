package com.amalvadkar.ihms.app.exceptions.handlers;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends IhmsException{

    public UnauthorizedAccessException() {
        super("You don't have permission", HttpStatus.FORBIDDEN.value());
    }
}

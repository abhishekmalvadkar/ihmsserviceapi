package com.amalvadkar.ihms.app.exceptions.handlers;

import lombok.Getter;

@Getter
public class IhmsException extends RuntimeException{

    private final Integer code;

    public IhmsException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}

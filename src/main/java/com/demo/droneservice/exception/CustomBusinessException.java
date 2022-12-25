package com.demo.droneservice.exception;


public class CustomBusinessException extends Exception{
    public CustomBusinessException(String errorMessage) {
        super(errorMessage);
    }
}

package com.binaryBaaje.ShiftSnap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends Exception{
    private String message;

    public ProfileNotFoundException(String message){
        super(message);
    }
}
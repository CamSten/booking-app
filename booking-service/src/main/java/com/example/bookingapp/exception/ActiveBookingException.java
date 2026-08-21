package com.example.bookingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActiveBookingException extends RuntimeException {

    public ActiveBookingException(String message) {
        super(message);
    }
}
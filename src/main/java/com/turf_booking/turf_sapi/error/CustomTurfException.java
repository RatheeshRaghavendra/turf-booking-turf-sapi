package com.turf_booking.turf_sapi.error;

import lombok.Getter;

@Getter
public class CustomTurfException extends RuntimeException {

    Exception exception;

    public CustomTurfException(String message) {
        super(message);
    }
    public CustomTurfException(Exception exception, String message){
        super(message);
        this.exception = exception;
    }
}

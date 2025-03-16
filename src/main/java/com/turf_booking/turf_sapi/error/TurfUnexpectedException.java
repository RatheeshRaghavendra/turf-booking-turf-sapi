package com.turf_booking.turf_sapi.error;

public class TurfUnexpectedException extends CustomTurfException {
    public TurfUnexpectedException(String message) {
        super(message);
    }
    public TurfUnexpectedException(Exception exception, String message) {
        super(exception, message);
    }
}

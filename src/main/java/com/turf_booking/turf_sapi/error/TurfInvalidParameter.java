package com.turf_booking.turf_sapi.error;

public class TurfInvalidParameter extends CustomTurfException {
    public TurfInvalidParameter(String message) {
        super(message);
    }
    public TurfInvalidParameter(Exception exception, String message) {
        super(exception, message);
    }
}

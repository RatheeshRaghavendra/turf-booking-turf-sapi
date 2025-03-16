package com.turf_booking.turf_sapi.error;

public class TurfAlreadyExists extends CustomTurfException {
    public TurfAlreadyExists(String message) {
        super(message);
    }
    public TurfAlreadyExists(Exception exception, String message) {
        super(exception, message);
    }
}

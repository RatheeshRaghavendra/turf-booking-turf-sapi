package com.turf_booking.turf_sapi.error;

public class SlotTurfAlreadyExists extends CustomTurfException {
    public SlotTurfAlreadyExists(String message) {
        super(message);
    }
    public SlotTurfAlreadyExists(Exception exception, String message) {
        super(exception, message);
    }
}

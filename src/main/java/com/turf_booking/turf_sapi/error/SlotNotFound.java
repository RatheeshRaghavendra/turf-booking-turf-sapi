package com.turf_booking.turf_sapi.error;

import jakarta.ws.rs.NotFoundException;

public class SlotNotFound extends CustomTurfException {
    public SlotNotFound(String message) {
        super(message);
    }
    public SlotNotFound(Exception exception, String message) {
        super(exception, message);
    }
}

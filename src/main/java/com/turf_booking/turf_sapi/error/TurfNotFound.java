package com.turf_booking.turf_sapi.error;

import java.util.NoSuchElementException;

public class TurfNotFound extends CustomTurfException {
  public TurfNotFound(String message) {
    super(message);
  }
  public TurfNotFound(Exception exception, String message) {
    super(exception, message);
  }
}

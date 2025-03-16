package com.turf_booking.turf_sapi.error;

public class PartialSuccess extends CustomTurfException{
	public PartialSuccess(String message) {
		super(message);
	}
	public PartialSuccess(Exception exception, String message) {
		super(exception, message);
	}
	
	

}

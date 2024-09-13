package com.turf_booking.turf_sapi.model;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class ApiError {
	
	String errorMessage;
	String errorDescription;
	@Nullable
	String customError;

	public ApiError() {
		super();
	}

	public ApiError(String errorMessage, String errorDescription, String customError) {
		super();
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
		this.customError = customError;
	}
	
	public void setApiErrorDetails(Exception e,String customError) {
		this.errorMessage = e.getMessage();
		this.errorDescription = e.toString();
		this.customError = customError;
	}
	
	

}

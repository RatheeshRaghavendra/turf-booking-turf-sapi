package com.turf_booking.turf_sapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.turf_booking.turf_sapi.dao.SlotDao;
import com.turf_booking.turf_sapi.dao.TurfDao;
import com.turf_booking.turf_sapi.error.PartialSuccess;
import com.turf_booking.turf_sapi.model.ApiError;
import com.turf_booking.turf_sapi.model.ApiResponse;
import com.turf_booking.turf_sapi.model.Slot;
import com.turf_booking.turf_sapi.model.Turf;

import jakarta.ws.rs.NotFoundException;

@Service
public class TurfService {
	
	@Autowired
	TurfDao turfDao;
	
	@Autowired
	SlotDao slotDao;
	
	public ResponseEntity<ApiResponse<Turf>> getTurfById(Integer turfId) {
		
		ApiResponse<Turf> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		Turf turf = new Turf();
		
		try {
		
		turf = turfDao.findById(turfId).get();
		apiResponse.setPayload(turf);
		
		} catch (Exception e) {
			e.printStackTrace();
			
			customError = "Error Message while searching for Turf ID: " + turfId + " in the DB";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setStatusCode(500);
			apiResponse.setApiError(apiError);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<List<Turf>>> getTurfsByParameter(String parameter, String value) {
		
		
		ApiResponse<List<Turf>> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			List<Turf> turfList = new ArrayList<>();
			
			switch (parameter.toLowerCase()) {
			case "name": 
				turfList = turfDao.findByName(value);
				break;
			
			case "city": 	
				turfList = turfDao.findByCity(value);
				break;
			
			case "area": 	
				turfList = turfDao.findByArea(value);
				break;
			
			case "sports": 	
				turfList = turfDao.findBySports(value);
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + parameter);
			}
			
			apiResponse.setPayload(turfList);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			customError = "Error while fetching Turfs by Parameter(" + parameter.toUpperCase() + ") as " + value;
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setStatusCode(500);
			apiResponse.setApiError(apiError);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
		
	}
	
	public ResponseEntity<ApiResponse<List<Turf>>> getAllTurfs() {
		
		ApiResponse<List<Turf>> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			List<Turf> turfList = turfDao.findAll();
			apiResponse.setPayload(turfList);
			
		} catch (Exception e) {
			e.printStackTrace();
		
			customError = "Error while fetching all the Turfs"; 
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setStatusCode(500);
			apiResponse.setApiError(apiError);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<String>> addTurf(Turf turf) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			Turf turfResponse =  turfDao.save(turf);
			apiResponse.setPayload("The Turf was successfully added. Here is the Turf ID: " + turfResponse.getTurfId());
			apiResponse.setStatusCode(201);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			customError = "Error while adding the Turf";
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setStatusCode(500);
			apiResponse.setApiError(apiError);
			
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> bookTurf(Integer turfId, List<Integer> slotIds) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			if(turfDao.existsById(turfId)) {
				
				Turf turf = turfDao.findById(turfId).get();
				
				List<Integer> newSlotIds = turf.getBookedSlotIds();
				newSlotIds.addAll(slotIds);
				turf.setBookedSlotIds(newSlotIds);
				
				turfDao.save(turf);
				
				apiResponse.setPayload("The slot was booked for the Turf ID: " + turfId);
			
			}
			else {
				throw new NotFoundException("The Turf was not found");
			}
			
		} catch (NotFoundException e) {
			
			e.printStackTrace();
			
			customError = "The Turf with Turf ID: " + turfId + " was not found in the Database";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(404);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			customError = "An Error occured while trying to book the slots";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
			
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> cancelTurf(Integer turfId, List<Integer> slotIds) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			if(turfDao.existsById(turfId)) {
				
				Turf turf = turfDao.findById(turfId).get();
				
				List<Integer> newSlotIds = turf.getBookedSlotIds();
				newSlotIds.removeAll(slotIds);
				turf.setBookedSlotIds(newSlotIds);
				
				turfDao.save(turf);
				
				apiResponse.setPayload("Cancellation was successful for the Turf ID: " + turfId);
			
			}
			else {
				throw new NotFoundException("The Turf was not found");
			}
			
		} catch (NotFoundException e) {
			
			e.printStackTrace();
			
			customError = "The Turf with Turf ID: " + turfId + " was not found in the Database";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(404);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			customError = "An Error occured while trying to cancel the slots";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
			
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<List<Slot>>> getSlots(List<Integer> slots) {
		
		ApiResponse<List<Slot>> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			Boolean raiseError = false;
			
			List<Integer> slotIdsNotAvaialble = new ArrayList<>();
			
			for(Integer slotId: slots) {
				
				if(!slotDao.existsById(slotId)) {
					raiseError = true;
					slotIdsNotAvaialble.add(slotId);
				}
				
			}
			
			List<Slot> slotsResponse =  slotDao.findAllById(slots);
			apiResponse.setPayload(slotsResponse);
			
			if (raiseError && slotIdsNotAvaialble.equals(slots)){
				throw new RuntimeException("No Slot IDs were found");
			}
			else if(raiseError) {
				throw new PartialSuccess("Not all Slot IDs were found");
			}
			
		} catch (PartialSuccess e) {
			
			e.printStackTrace();
			
			customError = "Partial Success while fetching the slot details";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(202);
			
		} catch (RuntimeException e) {
			
			e.printStackTrace();
			
			customError = "Failure. None of these Slot Ids were present";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(404);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			customError = "Error while fetching the Slots with the given Ids";
			
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
		
	}

	public ResponseEntity<ApiResponse<List<Slot>>> getAllSlots() {
		
		ApiResponse<List<Slot>> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			List<Slot> allSlots = slotDao.findAll();
			apiResponse.setPayload(allSlots);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			customError = "Error while fetching all the Slots";
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> addSlots(List<Slot> slots) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			slotDao.saveAll(slots);
			
			apiResponse.setPayload("Success. The Slots were created Successfully");
			apiResponse.setStatusCode(201);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			customError = "Error while creating the slots";
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);
			
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	

}

package com.turf_booking.turf_sapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import com.turf_booking.turf_sapi.error.*;
import com.turf_booking.turf_sapi.logger.GlobalLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.turf_booking.turf_sapi.dao.SlotDao;
import com.turf_booking.turf_sapi.dao.TurfDao;
import com.turf_booking.turf_sapi.model.ApiError;
import com.turf_booking.turf_sapi.model.ApiResponse;
import com.turf_booking.turf_sapi.model.Turf;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


@Service
@Log4j2
public class TurfService {

	private final String prefix = GlobalLog.prefix + getClass().getSimpleName() + "::"; // appName + "::" +
	
	@Autowired
	TurfDao turfDao;
	
//	@Autowired
//	SlotDao slotDao;
	
	public ResponseEntity<ApiResponse<Turf>> getTurfById(Integer turfId) {

		ApiResponse<Turf> apiResponse = new ApiResponse<>();
		try {
			Turf turf = turfDao.findById(turfId).orElseThrow();
			log.debug(prefix + "getTurfById::" + turf);
			apiResponse.setPayload(turf);
		} catch (NoSuchElementException e) {
			log.error(prefix + "getTurfById::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfNotFound("No Turf with the Turf ID: "+ turfId +", Present in the DB");
		}
		catch (Exception e) {
			log.error(prefix + "getTurfById::" + e.getMessage());
			throw new TurfUnexpectedException(e,"Unexpected Error while searching for Turf ID: " + turfId + " in the DB. Please check the Error logs for more info");
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<List<Turf>>> getTurfsByParameter(String parameter, String value) {

		ApiResponse<List<Turf>> apiResponse = new ApiResponse<>();
		try {
			List<Turf> turfList = switch (parameter.toLowerCase()) {
                case "name" -> turfDao.findByName(value);
                case "city" -> turfDao.findByCity(value);
                case "area" -> turfDao.findByArea(value);
                case "sports" -> turfDao.findBySports(value);
                default -> throw new IllegalArgumentException("Unexpected value: " + parameter);
            };
            if (turfList.isEmpty()){
				throw new TurfNotFound("No Turfs found for Parameter(" + parameter.toUpperCase() + ") as " + value);
			}
			apiResponse.setPayload(turfList);
			
		} catch (IllegalArgumentException e) {
			log.error(prefix + "getTurfsByParameter::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfInvalidParameter(e,"Invalid Parameter: " + parameter.toUpperCase());
		} catch (TurfNotFound e) {
			log.error(prefix + "getTurfsByParameter::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(prefix + "getTurfsByParameter::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfUnexpectedException(e, "Unexpected Error while getting turfs with Parameter: " + parameter + " and Value: " + value);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
		
	}
	
	public ResponseEntity<ApiResponse<List<Turf>>> getAllTurfs() {
		
		ApiResponse<List<Turf>> apiResponse = new ApiResponse<>();
		try {
			List<Turf> turfList = turfDao.findAll();
			if (turfList.isEmpty()){
				throw new TurfNotFound("No Turfs found in the database");
			}
			apiResponse.setPayload(turfList);
		} catch (TurfNotFound e) {
			log.error(prefix + "getAllTurfs::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(prefix + "getAllTurfs::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfUnexpectedException(e,"Unexpected Error while getting all the turfs");
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

	public ResponseEntity<ApiResponse<String>> addTurf(Turf turf) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		try {
			if((turfDao.findIfTurfExists(
					turf.getName(),
					turf.getCity(),
					turf.getArea(),
					turf.getAddress(),
					turf.getSports())) != null){
				throw new TurfAlreadyExists("Turf already exists");
			}
			Turf turfResponse =  turfDao.save(turf);
			apiResponse.setPayload("The Turf was successfully added. Here is the Turf ID: " + turfResponse.getTurfId());
			apiResponse.setStatusCode(201);
			
		} catch (HandlerMethodValidationException e){
			log.error(prefix + "addTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (TurfAlreadyExists e) {
			log.error(prefix + "addTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(prefix + "addTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfUnexpectedException(e,"Unexpected Error while adding the turf");
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> bookTurf(Integer turfId, List<String> slotIds) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		try {
			Turf turf = turfDao.findById(turfId).orElseThrow();
			List<String> newSlotIds = turf.getBookedSlotIds();
			for(String slotId: slotIds)
				if(newSlotIds.contains(slotId))
					throw new SlotTurfAlreadyExists(slotId + " This slot is already Booked");
			newSlotIds.addAll(slotIds);
			turf.setBookedSlotIds(newSlotIds);
			turfDao.save(turf);
			apiResponse.setPayload("The slotList was booked for the Turf ID: " + turfId);
		} catch (NoSuchElementException e) {
			log.error(prefix + "bookTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfNotFound("Turf with Turf ID: " + turfId + " was not found");
		} catch (SlotTurfAlreadyExists e) {
			log.error(prefix + "bookTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(prefix + "bookTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfUnexpectedException(e,"Unexpected Error while Booking the Turf with Turf ID: " + turfId);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
	
	public ResponseEntity<ApiResponse<String>> cancelTurf(Integer turfId, List<String> slotIds) {
		
		ApiResponse<String> apiResponse = new ApiResponse<>();
		try {
			Turf turf = turfDao.findById(turfId).orElseThrow();
			List<String> newSlotIds = turf.getBookedSlotIds();
			for(String slotId: slotIds)
				if(!newSlotIds.contains(slotId))
					throw new SlotNotFound(slotId + " This slot hasn't been booked");
			newSlotIds.removeAll(slotIds);
			turf.setBookedSlotIds(newSlotIds);
			turfDao.save(turf);
			apiResponse.setPayload("Cancellation was successful for the Turf ID: " + turfId);
		} catch (NoSuchElementException e) {
			log.error(prefix + "cancelTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfNotFound("Turf with Turf ID: " + turfId + " was not found");
		} catch (Exception e) {
			log.error(prefix + "cancelTurf::CAUSE::" + e.getClass().getSimpleName() + "::DESCRIPTION::" + e.getMessage());
			throw new TurfUnexpectedException(e, "Unexpected Error while cancelling the slot for Turf with Turf ID: " + turfId);
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}

/*
	public ResponseEntity<ApiResponse<List<Slot>>> getSlots(List<String> slots) {
		
		ApiResponse<List<Slot>> apiResponse = new ApiResponse<>();
		ApiError apiError = new ApiError();
		String customError = "";
		
		try {
			
			Boolean raiseError = false;
			
			List<String> slotIdsNotAvaialble = new ArrayList<>();
			
			for(String slotId: slots) {
				
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
			
			customError = "Partial Success while fetching the slotList details";
			
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
			List<Slot> slotList = new ArrayList<>();

			for(Slot slot: slots){
//				Slot slotList = new Slot();
				slot.setSlotId();
			}

			slotDao.saveAll(slotList);
			
			apiResponse.setPayload("Success. The Slots were created Successfully");
			apiResponse.setStatusCode(201);
			
		} catch (ValidationException e) {

			e.printStackTrace();

			customError = "Error while creating the slots";
			apiError.setApiErrorDetails(e, customError);

			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);

		} catch (Exception e) {
			
			e.printStackTrace();
			
			customError = "Error while creating the slots";
			apiError.setApiErrorDetails(e, customError);
			
			apiResponse.setApiError(apiError);
			apiResponse.setStatusCode(500);

			System.out.println(e.getCause().toString());
			
		}
		
		return new ResponseEntity<>(apiResponse,apiResponse.getStatusMessage());
	}
*/

}

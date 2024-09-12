package com.turf_booking.turf_sapi.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.turf_booking.turf_sapi.dao.SlotDao;
import com.turf_booking.turf_sapi.dao.TurfDao;
import com.turf_booking.turf_sapi.model.Slot;
import com.turf_booking.turf_sapi.model.Turf;

@Service
public class TurfService {
	
	@Autowired
	TurfDao turfDao;
	
	@Autowired
	SlotDao slotDao;
	
	public ResponseEntity<Turf> getTurfById(Integer turfId) {
		
		Turf turf = new Turf();
		
		try {
		
		turf = turfDao.findById(turfId).get();
		
		return new ResponseEntity<>(turf,HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(turf,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<List<Turf>> getTurfsByParameter(String parameter, String value) {
		
		List<Turf> turfList = new ArrayList<>();
		
		try {
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
			
			return new ResponseEntity<>(turfList,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	public ResponseEntity<String> addTurf(Turf turf) {
		
		try {
			Turf turfResponse =  turfDao.save(turf);
			System.out.println("Turf: " + turfResponse.toString());
			return new ResponseEntity<>("Success: " + turfResponse.getTurfId().toString() ,HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> addSlots(List<Slot> slots) {
		
		try {
			slotDao.saveAll(slots);
			return new ResponseEntity<>("Success",HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>("Failed",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<List<Slot>> getSlots(List<Integer> slots) {
		
		try {
			
			List<Slot> slotsResponse =  slotDao.findAllById(slots);
			return new ResponseEntity<>(slotsResponse,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	public ResponseEntity<List<Slot>> getAllSlots() {
		
		try {
			
			List<Slot> allSlots = slotDao.findAll();
			return new ResponseEntity<>(allSlots,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

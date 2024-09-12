package com.turf_booking.turf_sapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turf_booking.turf_sapi.model.Slot;
import com.turf_booking.turf_sapi.model.Turf;
import com.turf_booking.turf_sapi.service.TurfService;

@RestController
@RequestMapping("api/turf-sapi")
public class TurfController {
	
	@Autowired
	TurfService turfService;
	
	@GetMapping("live")
	public String getHealth() {
		return "live";
	}
	
	@GetMapping("turf/{turfId}")
	public ResponseEntity<Turf> getTurfById (@PathVariable Integer turfId){
		
		return turfService.getTurfById(turfId);
		
	}
	
	@GetMapping("turf/search-by")
	public ResponseEntity<List<Turf>> getTurfsByParameter (@RequestParam String parameter, @RequestParam String value){
		
		return turfService.getTurfsByParameter(parameter,value);
	}

	
	@PostMapping("turf")
	public ResponseEntity<String> addTurf (@RequestBody Turf turf){
		
		return turfService.addTurf(turf);
		
	}
	
	@GetMapping("slots")
	public ResponseEntity<List<Slot>> getSlots (@RequestParam List<Integer> slots){
		
		return turfService.getSlots(slots);
	}
	
	@GetMapping("slots/all")
	public ResponseEntity<List<Slot>> getAllSlots (){
		
		return turfService.getAllSlots();
	}
	
	@PostMapping("slots")
	public ResponseEntity<String> addSlots (@RequestBody List<Slot> slots){
		
		return turfService.addSlots(slots);
		
	}
	
}

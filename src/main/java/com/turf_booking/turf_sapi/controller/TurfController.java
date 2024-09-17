package com.turf_booking.turf_sapi.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turf_booking.turf_sapi.model.ApiResponse;
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
	public ResponseEntity<ApiResponse<Turf>> getTurfById (@PathVariable Integer turfId){
		
		return turfService.getTurfById(turfId);
		
	}
	
	@GetMapping("turf/search-by")
	public ResponseEntity<ApiResponse<List<Turf>>> getTurfsByParameter (@RequestParam String parameter, @RequestParam String value){
		
		return turfService.getTurfsByParameter(parameter,value);
	}
	
	@GetMapping("turf/all")
	public ResponseEntity<ApiResponse<List<Turf>>> getAllTurf(){
		
		return turfService.getAllTurfs();
	}

	
	@PostMapping("turf")
	public ResponseEntity<ApiResponse<String>> addTurf (@Valid @RequestBody Turf turf){
		
		return turfService.addTurf(turf);		
	}
	
	@PatchMapping("turf/book/{turfId}")
	public ResponseEntity<ApiResponse<String>> bookTurf (@PathVariable Integer turfId,@RequestParam List<String> slotIds){
		
		return turfService.bookTurf(turfId, slotIds);
	}
	
	@DeleteMapping("turf/cancel/{turfId}")
	public ResponseEntity<ApiResponse<String>> cancelTurf (@PathVariable Integer turfId,@RequestParam List<String> slotIds){
		
		return turfService.cancelTurf(turfId, slotIds);
	}
	
	@GetMapping("slots")
	public ResponseEntity<ApiResponse<List<Slot>>> getSlots (@RequestParam List<String> slots){
		
		return turfService.getSlots(slots);
	}
	
	@GetMapping("slots/all")
	public ResponseEntity<ApiResponse<List<Slot>>> getAllSlots (){
		
		return turfService.getAllSlots();
	}
	
	@PostMapping("slots")
	public ResponseEntity<ApiResponse<String>> addSlots (@Valid @RequestBody List<Slot> slots){
		
		return turfService.addSlots(slots);
		
	}
	
}

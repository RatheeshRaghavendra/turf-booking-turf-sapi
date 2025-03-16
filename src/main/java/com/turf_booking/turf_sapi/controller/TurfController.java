package com.turf_booking.turf_sapi.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turf_booking.turf_sapi.model.ApiResponse;
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
	
	@PutMapping("turf/book/{turfId}")
	public ResponseEntity<ApiResponse<String>> bookTurf (@PathVariable Integer turfId,@RequestBody List<
			@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}\\$\\d{1,2}((am)|(pm))-\\d{1,2}((am)|(pm))", message = "bookedSlotIds should be of the pattern '01-01-1999$5am-6am'")
					String> slotIds){
		return turfService.bookTurf(turfId, slotIds);
	}
	
	@DeleteMapping("turf/cancel/{turfId}")
	public ResponseEntity<ApiResponse<String>> cancelTurf (@PathVariable Integer turfId,@RequestParam List<
			@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}\\$\\d{1,2}((am)|(pm))-\\d{1,2}((am)|(pm))", message = "bookedSlotIds should be of the pattern '01-01-1999$5am-6am'")
					String> slotIds){
		return turfService.cancelTurf(turfId, slotIds);
	}
}

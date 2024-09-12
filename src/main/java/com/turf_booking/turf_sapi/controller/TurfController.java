package com.turf_booking.turf_sapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/turf")
public class TurfController {
	
	@GetMapping("live")
	public String getHealth() {
		return "live";
	}

}

package com.turf_booking.turf_sapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class TurfSapiApplication {

	@Autowired
	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(TurfSapiApplication.class, args);
	}

}

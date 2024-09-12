package com.turf_booking.turf_sapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Slot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer slotId;
	String day;
	String month;
	String year;
	String slotTime;
	
	public Slot(String day, String month, String year, String slotTime) {
		super();
		
		this.day = day;
		this.month = month;
		this.year = year;
		this.slotTime = slotTime;
	}

	public Slot() {
		super();
		
	}
	
	
	
	
}

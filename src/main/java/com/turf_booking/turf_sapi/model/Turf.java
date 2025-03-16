package com.turf_booking.turf_sapi.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Turf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer turfId;
	String name;
	String city;
	String area;
	String address;
	String sports;
	List<@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}\\$\\d{1,2}((am)|(pm))-\\d{1,2}((am)|(pm))", message = "bookedSlotIds should be of the pattern '01-01-1999$5am-6am'")
			String> bookedSlotIds;
	
	Integer pricePerHour;
	
	public Turf(Integer turfId, String name, String city, String area, String address, String sports,
			List<String> bookedSlotIds, Integer pricePerHour) {
		super();
		this.turfId = turfId;
		this.name = name;
		this.city = city;
		this.area = area;
		this.address = address;
		this.sports = sports;
		this.bookedSlotIds = bookedSlotIds;
		this.pricePerHour = pricePerHour;
	}

	public Turf() {
		super();
	}
	
	

}

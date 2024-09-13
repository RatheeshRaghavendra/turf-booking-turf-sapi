package com.turf_booking.turf_sapi.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@ElementCollection
	List<Integer> bookedSlotIds;
	
	Integer pricePerHour;
	
	public Turf(Integer turfId, String name, String city, String area, String address, String sports,
			List<Integer> bookedSlotIds, Integer pricePerHour) {
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

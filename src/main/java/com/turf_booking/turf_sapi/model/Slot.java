package com.turf_booking.turf_sapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Slot {

	@Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}\\$\\d{1,2}((am)|(pm))-\\d{1,2}((am)|(pm))", message = "Id should be of the pattern '01-01-1999$5am-6am'")
	@Id
	String slotId;
	@Pattern(regexp = "\\d{2}", message = "'day' field should be of the pattern '01'")
	@Min(value = 1, message = "Minimum date is 01")
	@Max(value = 31, message = "Maximum date is 31")
	String day;
	@Pattern(regexp = "\\d{2}", message = "'month' field should be of the pattern '03'")
	@Min(value = 1, message = "Minimum month is 01")
	@Max(value = 12, message = "Maximum month is 12")
	String month;
	@Pattern(regexp = "\\d{4}", message = "'year' field should be of the pattern '1999'")
	String year;
	@Pattern(regexp = "\\d{1,2}((am)|(pm))-\\d{1,2}((am)|(pm))", message = "'slotTime' field should match the pattern '5am-6pm'")
	String slotTime;
	
	public Slot(String day, String month, String year, String slotTime) {
		super();

		this.slotId = day + "-" + month + "-" + year + "$" + slotTime;
		this.day = day;
		this.month = month;
		this.year = year;
		this.slotTime = slotTime;
	}

	public Slot() {
		super();
		this.slotId = this.day + "-" + this.month + "-" + this.year + "$" + this.slotTime;
		
	}

	public void setSlotId (){

		this.slotId = this.day + "-" + this.month + "-" + this.year + "$" + this.slotTime;
//		return this;
    }
	
	
	
	
}

package com.turf_booking.turf_sapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turf_booking.turf_sapi.model.Turf;

@Repository
public interface TurfDao extends JpaRepository<Turf, Integer>{
	
	List<Turf> findByName(String name);
	
	List<Turf> findByCity(String city);
	
	List<Turf> findByArea(String area);
	
	List<Turf> findBySports(String sports);

}

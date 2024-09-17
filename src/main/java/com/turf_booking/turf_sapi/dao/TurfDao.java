package com.turf_booking.turf_sapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turf_booking.turf_sapi.model.Turf;

@Repository
public interface TurfDao extends JpaRepository<Turf, Integer>{
	
	List<Turf> findByName(String name);
	
	List<Turf> findByCity(String city);
	
	List<Turf> findByArea(String area);
	
	List<Turf> findBySports(String sports);

	@Query(value = "SELECT t.turf_id FROM Turf t WHERE t.name=:name AND t.city=:city AND t.area=:area AND t.address=:address AND t.sports=:sports", nativeQuery = true)
	String findIfTurfExists(String name, String city, String area, String address, String sports);

}

package com.turf_booking.turf_sapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turf_booking.turf_sapi.model.Slot;

@Repository
public interface SlotDao extends JpaRepository<Slot, String>{

}

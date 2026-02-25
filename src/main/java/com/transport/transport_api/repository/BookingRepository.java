package com.transport.transport_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.transport_api.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBusIdAndSeatNumber(Long busId, Integer seatNumber);
}
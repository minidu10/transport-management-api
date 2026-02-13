package com.transport.transport_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.transport_api.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

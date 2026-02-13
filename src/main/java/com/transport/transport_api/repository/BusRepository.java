package com.transport.transport_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.transport_api.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {
}

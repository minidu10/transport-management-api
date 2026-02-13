package com.transport.transport_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.transport_api.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}

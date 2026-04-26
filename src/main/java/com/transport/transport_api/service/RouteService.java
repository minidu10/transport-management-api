package com.transport.transport_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.transport_api.entity.Route;
import com.transport.transport_api.exception.ResourceNotFoundException;
import com.transport.transport_api.repository.RouteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public void deleteRoute(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
        routeRepository.delete(route);
    }
}

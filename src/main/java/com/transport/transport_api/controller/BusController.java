package com.transport.transport_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.transport_api.entity.Bus;
import com.transport.transport_api.service.BusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    // Only ADMIN can create
@PostMapping
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Bus> createBus(@Valid @RequestBody Bus bus) {
    Bus savedBus = busService.createBus(bus);
    return ResponseEntity.status(201).body(savedBus);
}

// Only ADMIN can delete
@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
    busService.deleteBus(id);
    return ResponseEntity.noContent().build();
}

// USER and ADMIN can view
@GetMapping
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public ResponseEntity<List<Bus>> getAllBuses() {
    return ResponseEntity.ok(busService.getAllBuses());
}
}
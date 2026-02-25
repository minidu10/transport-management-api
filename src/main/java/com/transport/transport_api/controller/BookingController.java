package com.transport.transport_api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.transport_api.entity.Booking;
import com.transport.transport_api.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{busId}")
    public Booking bookSeat(@PathVariable Long busId,
                            @RequestParam Integer seatNumber) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return bookingService.bookSeat(busId, seatNumber, username);
    }
}
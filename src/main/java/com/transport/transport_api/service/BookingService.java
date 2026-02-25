package com.transport.transport_api.service;

import org.springframework.stereotype.Service;

import com.transport.transport_api.entity.AppUser;
import com.transport.transport_api.entity.Booking;
import com.transport.transport_api.entity.Bus;
import com.transport.transport_api.exception.SeatAlreadyBookedException;
import com.transport.transport_api.repository.AppUserRepository;
import com.transport.transport_api.repository.BookingRepository;
import com.transport.transport_api.repository.BusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final AppUserRepository userRepository;

    public Booking bookSeat(Long busId, Integer seatNumber, String username) {

        if (bookingRepository.findByBusIdAndSeatNumber(busId, seatNumber).isPresent()) {
        throw new SeatAlreadyBookedException("Seat already booked");
        }

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setBus(bus);
        booking.setUser(user);
        booking.setSeatNumber(seatNumber);

        return bookingRepository.save(booking);
    }
}
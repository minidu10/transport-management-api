package com.transport.transport_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.transport_api.entity.Bus;
import com.transport.transport_api.repository.BusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}
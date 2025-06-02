package com.data.service;

import com.data.model.Trip;
import com.data.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImp implements TripService {
    @Autowired
    private TripRepository TripRepository;

    @Override
    public List<Trip> getTrips(String departure, String destination, int page, int size) {
        int offset = (page - 1) * size;
        return TripRepository.searchTrips(departure, destination, offset, size);
    }

    @Override
    public int getTotalPages(String departure, String destination, int size) {
        int total = TripRepository.countTrips(departure, destination);
        return (int) Math.ceil((double) total / size);
    }
}
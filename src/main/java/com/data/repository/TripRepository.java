package com.data.repository;

import com.data.model.Trip;

import java.util.List;

public interface TripRepository {
    List<Trip> searchTrips(String departure, String destination, int offset, int limit);
    int countTrips(String departure, String destination);
}
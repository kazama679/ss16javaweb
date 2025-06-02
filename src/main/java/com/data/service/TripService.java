package com.data.service;

import com.data.model.Trip;

import java.util.List;

public interface TripService {
    List<Trip> getTrips(String departure, String destination, int page, int size);
    int getTotalPages(String departure, String destination, int size);
}
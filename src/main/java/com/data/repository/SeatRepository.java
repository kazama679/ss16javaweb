package com.data.repository;

import com.data.model.Seat;

import java.util.List;

public interface SeatRepository {
    void save(Seat seat);
    List<Seat> findByBusId(int busId);
    void deleteByBusId(int busId);
}
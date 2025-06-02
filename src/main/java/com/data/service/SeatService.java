package com.data.service;

import com.data.dto.SeatDTO;
import com.data.model.Bus;

import java.util.List;

public interface SeatService {
    void generateSeatsForBus(Bus bus);
    void deleteByBusId(int busId);
    List<SeatDTO> getSeatsByBus(int busId);
}
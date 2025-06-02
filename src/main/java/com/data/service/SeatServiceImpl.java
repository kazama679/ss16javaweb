package com.data.service;

import com.data.dto.SeatDTO;
import com.data.model.Bus;
import com.data.model.Seat;
import com.data.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepo;

    @Override
    public void generateSeatsForBus(Bus bus) {
        int rows = bus.getRowSeat();
        int cols = bus.getColSeat();
        double price;
        switch (bus.getBusType()) {
            case "VIP":
                price = 150000;
                break;
            case "LUXURY":
                price = 200000;
                break;
            default:
                price = 100000;
        }

        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                Seat seat = new Seat();
                seat.setNameSeat((char) ('A' + (i - 1)) + String.valueOf(j));
                seat.setPrice(price);
                seat.setBusId(bus.getId());
                seat.setStatus(false);
                seatRepo.save(seat);
            }
        }
    }

    @Override
    public void deleteByBusId(int busId) {
        seatRepo.deleteByBusId(busId);
    }

    @Override
    public List<SeatDTO> getSeatsByBus(int busId) {
        List<Seat> seats = seatRepo.findByBusId(busId);
        List<SeatDTO> dtos = new ArrayList<>();
        for (Seat s : seats) {
            SeatDTO dto = new SeatDTO();
            dto.setId(s.getId());
            dto.setNameSeat(s.getNameSeat());
            dto.setPrice(s.getPrice());
            dto.setBusId(s.getBusId());
            dto.setStatus(s.isStatus());
            dtos.add(dto);
        }
        return dtos;
    }
}

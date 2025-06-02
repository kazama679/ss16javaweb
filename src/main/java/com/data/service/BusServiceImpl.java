package com.data.service;

import com.data.dto.BusDTO;
import com.data.model.Bus;
import com.data.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepo;
    @Autowired
    private SeatService seatService;

    @Override
    public List<BusDTO> getAll() {
        return busRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BusDTO findById(int id) {
        return convertToDTO(busRepo.findById(id));
    }

    @Override
    public void save(BusDTO dto) {
        Bus bus = convertToEntity(dto);
        bus.setTotalSeat(bus.getRowSeat() * bus.getColSeat());
        busRepo.save(bus);
        seatService.generateSeatsForBus(bus);
    }

    @Override
    public void update(BusDTO dto) {
        Bus bus = convertToEntity(dto);
        bus.setTotalSeat(bus.getRowSeat() * bus.getColSeat());
        busRepo.update(bus);
        seatService.deleteByBusId(bus.getId());
        seatService.generateSeatsForBus(bus);
    }

    @Override
    public void delete(int id) {
        seatService.deleteByBusId(id);
        busRepo.delete(id);
    }

    private BusDTO convertToDTO(Bus bus) {
        BusDTO dto = new BusDTO();
        dto.setId(bus.getId());
        dto.setLicensePlate(bus.getLicensePlate());
        dto.setBusType(bus.getBusType());
        dto.setRowSeat(bus.getRowSeat());
        dto.setColSeat(bus.getColSeat());
        dto.setTotalSeat(bus.getTotalSeat());
        dto.setImage(bus.getImage());
        return dto;
    }

    private Bus convertToEntity(BusDTO dto) {
        Bus bus = new Bus();
        bus.setId(dto.getId());
        bus.setLicensePlate(dto.getLicensePlate());
        bus.setBusType(dto.getBusType());
        bus.setRowSeat(dto.getRowSeat());
        bus.setColSeat(dto.getColSeat());
        bus.setImage(dto.getImage());
        return bus;
    }
}

package com.data.service;

import com.data.dto.BusDTO;
import com.data.model.Bus;

import java.util.List;

public interface BusService {
    List<BusDTO> getAll();
    BusDTO findById(int id);
    void save(BusDTO dto);
    void update(BusDTO dto);
    void delete(int id);
}
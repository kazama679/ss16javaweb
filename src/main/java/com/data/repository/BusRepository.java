package com.data.repository;

import com.data.model.Bus;

import java.util.List;

public interface BusRepository {
    List<Bus> findAll();
    Bus findById(int id);
    void save(Bus bus);
    void update(Bus bus);
    void delete(int id);
}
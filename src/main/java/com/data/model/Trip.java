package com.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private int id;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private double price;
}

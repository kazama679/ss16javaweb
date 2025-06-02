package com.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private int id;
    private String nameSeat;
    private double price;
    private int busId;
    private boolean status;
}

package com.data.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private int id;

    @NotBlank(message = "Biển số không được để trống")
    private String licensePlate;

    @NotBlank(message = "Loại xe không được để trống")
    private String busType;

    @Min(value = 1, message = "Số hàng ghế phải lớn hơn 0")
    private int rowSeat;

    @Min(value = 1, message = "Số cột ghế phải lớn hơn 0")
    private int colSeat;

    private int totalSeat;
    private String image;
}
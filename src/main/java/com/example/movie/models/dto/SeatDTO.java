package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
    private int id;
    private int number;
    private String line;
    private int seatStatusId;
    private int roomId;
    private int seatTypeId;
    private boolean isActive;
}

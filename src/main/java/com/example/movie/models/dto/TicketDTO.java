package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private int id;
    private String code;
    private int scheduleId;
    private int seatId;
    private double priceTicket;
    private boolean isActive;
}

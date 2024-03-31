package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillTicketDTO {
    private int id;
    private int quantity;
    private int billId;
    private int ticketId;
}

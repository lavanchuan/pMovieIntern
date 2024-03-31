package com.example.movie.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillRequest {
    private String token;
    private int scheduleId;
    private int foodId;
    private int ticketQuantity; // phan biet ticket
    private int foodQuantity;
    private int promotionId;
}

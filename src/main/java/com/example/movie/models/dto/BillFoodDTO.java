package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillFoodDTO {
    private int id;
    private int quantity;
    private int billId;
    private int foodId;
}

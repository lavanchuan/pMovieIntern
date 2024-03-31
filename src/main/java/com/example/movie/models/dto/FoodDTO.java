package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private int id;
    private String nameOfFood;
    private String description;
    private String image;
    private double price;
    private boolean isActive;
}

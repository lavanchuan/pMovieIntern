package com.example.movie.models.dto;

import lombok.Data;

@Data
public class FoodFeaturedDTO {
    private int id;
    private String foodName;
    private int quantity;

    public static final String MATCH_PATTERN = "---";

    public FoodFeaturedDTO(String foodString){
        this.id = Integer.parseInt(foodString.split(MATCH_PATTERN)[0]);
        this.foodName = foodString.split(MATCH_PATTERN)[1];
        this.quantity = Integer.parseInt(foodString.split(MATCH_PATTERN)[2]);
    }
}

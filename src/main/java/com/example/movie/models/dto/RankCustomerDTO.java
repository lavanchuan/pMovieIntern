package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankCustomerDTO {
    private int id;
    private int point;
    private String description;
    private String name;
    private boolean isActive;
}

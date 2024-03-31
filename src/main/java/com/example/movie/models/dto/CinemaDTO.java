package com.example.movie.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {

    private int id;
    private String address;
    private String description;
    private String code;
    private String nameOfCinema;
    private boolean isActive;
}

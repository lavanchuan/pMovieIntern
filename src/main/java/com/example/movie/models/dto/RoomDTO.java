package com.example.movie.models.dto;

import com.example.movie.models.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private int id;
    private int capacity;
    private RoomType type;
    private String description;
    private int cinemaId;
    private String code;
    private String name;
    private boolean isActive;
}

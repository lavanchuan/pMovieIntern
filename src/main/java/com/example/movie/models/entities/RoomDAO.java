package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDAO extends BaseEntity {
    private int capacity;
    @Enumerated(EnumType.ORDINAL)
    private RoomType type;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaId")
    private CinemaDAO cinemaDAO;
    private String code;
    private String name;
    private boolean isActive;
}

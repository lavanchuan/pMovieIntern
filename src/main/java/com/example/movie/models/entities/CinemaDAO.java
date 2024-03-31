package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cinema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDAO extends BaseEntity {
    private String address;
    private String description;
    private String code;
    private String nameOfCinema;
    private boolean isActive;
}

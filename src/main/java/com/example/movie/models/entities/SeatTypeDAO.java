package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SeatType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatTypeDAO extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private SeatType nameType;
}

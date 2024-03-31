package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.SeatStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SeatStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatStatusDAO extends BaseEntity {
    private String code;
    @Enumerated(EnumType.STRING)
    private SeatStatus nameStatus;

    public SeatStatusDAO(SeatStatus status){
        super();
        nameStatus = status;
    }
}

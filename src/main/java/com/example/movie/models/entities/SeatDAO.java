package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDAO extends BaseEntity {
    private int number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatStatusId")
    private SeatStatusDAO seatStatusDAO;
    private String line;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private RoomDAO roomDAO;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatTypeId")
    private SeatTypeDAO seatTypeDAO;
}

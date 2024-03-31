package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDAO extends BaseEntity {
    private double price;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieId")
    private MovieDAO movieDAO;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private RoomDAO roomDAO;
    private boolean isActive;
}

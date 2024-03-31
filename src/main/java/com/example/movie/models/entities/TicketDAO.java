package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDAO extends BaseEntity {
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private ScheduleDAO scheduleDAO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId")
    private SeatDAO seatDAO;
    private double priceTicket;
    private boolean isActive;
}

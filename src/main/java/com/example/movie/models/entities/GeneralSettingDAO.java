package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "GenetalSetting")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralSettingDAO extends BaseEntity {
    private LocalDateTime breakTime;
    private int businessHours;
    private LocalDateTime closeTime;
    private double fixedTicketPrice;
    private int percentDay;
    private int percentWeekend;
    private LocalDateTime timeBeginToChange;
}

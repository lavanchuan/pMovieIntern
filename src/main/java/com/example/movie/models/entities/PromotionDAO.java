package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDAO extends BaseEntity {
    private int percent;
    private int quantity;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String name;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rankCustomerId")
    private RankCustomerDAO rankCustomerDAO;
}

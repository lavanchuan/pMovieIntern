package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDAO extends BaseEntity {
    private double totalMoney;
    private String tradingCode;
    private LocalDateTime createTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private UserDAO customer;
    private String name;
    private LocalDateTime updateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotionId")
    private PromotionDAO promotionDAO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billStatusId")
    private BillStatusDAO billStatusDAO;
    private boolean isActive;
}

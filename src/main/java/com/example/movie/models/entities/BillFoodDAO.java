package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BillFood")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillFoodDAO extends BaseEntity {
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId")
    private BillDAO billDAO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodId")
    private FoodDAO foodDAO;
}

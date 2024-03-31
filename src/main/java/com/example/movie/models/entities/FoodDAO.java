package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name = "Food")
@AllArgsConstructor
@NoArgsConstructor
public class FoodDAO extends BaseEntity {
    private double price;
    private String description;
    private String image;
    private String nameOfFood;
    private boolean isActive;
}

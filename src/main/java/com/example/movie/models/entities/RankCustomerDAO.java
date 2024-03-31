package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RankCustomer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankCustomerDAO extends BaseEntity {
    private int point;
    private String description;
    private String name;
    private boolean isActive;
}

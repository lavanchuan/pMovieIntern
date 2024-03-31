package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.BillStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "BillStatus")
@NoArgsConstructor
@AllArgsConstructor
public class BillStatusDAO extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private BillStatus name;
}

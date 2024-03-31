package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Banner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDAO extends BaseEntity {
    private String imageUrl;
    private String title;
}

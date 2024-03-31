package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDAO extends BaseEntity {
    private int movieDuration;
    private LocalDateTime endTime;
    private LocalDateTime premiereDate;
    private String description;
    private String director;
    private String image;
    private String heroImage;
    private String language;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieTypeId")
    private MovieTypeDAO movieTypeDAO;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rateId")
    private RateDAO rateDAO;
    private String trailer;
    private boolean isActive;
}

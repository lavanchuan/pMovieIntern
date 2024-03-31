package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import com.example.movie.models.MovieType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MovieType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTypeDAO extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private MovieType movieTypeName;
    private boolean isActive;

    public MovieTypeDAO(MovieType movieTypeName) {
        this.movieTypeName = movieTypeName;
    }
}

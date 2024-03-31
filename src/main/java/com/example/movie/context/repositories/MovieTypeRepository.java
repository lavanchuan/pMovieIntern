package com.example.movie.context.repositories;

import com.example.movie.models.entities.MovieTypeDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieTypeDAO, Integer> {
}

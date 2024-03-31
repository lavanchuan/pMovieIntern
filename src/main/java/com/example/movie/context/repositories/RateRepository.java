package com.example.movie.context.repositories;

import com.example.movie.models.entities.RateDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<RateDAO, Integer> {
}

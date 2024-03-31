package com.example.movie.context.repositories;

import com.example.movie.models.entities.SeatTypeDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatTypeDAO, Integer> {
}

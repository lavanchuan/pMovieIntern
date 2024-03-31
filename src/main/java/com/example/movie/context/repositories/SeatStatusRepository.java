package com.example.movie.context.repositories;

import com.example.movie.models.SeatStatus;
import com.example.movie.models.entities.SeatStatusDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatusDAO, Integer> {


    SeatStatusDAO findByNameStatus(SeatStatus seatStatus);
}

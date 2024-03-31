package com.example.movie.context.repositories;

import com.example.movie.models.entities.CinemaDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<CinemaDAO, Integer> {

    @Query(value = "select distinct cinema.* from cinema \n" +
            "inner join room on cinema.id = room.cinemaId \n" +
            "inner join schedule on room.id = schedule.roomId \n" +
            "where movieId = :movieId", nativeQuery = true)
    List<CinemaDAO> findAllByMovieId(int movieId);
}

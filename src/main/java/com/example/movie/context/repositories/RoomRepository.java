package com.example.movie.context.repositories;

import com.example.movie.models.dto.RoomDTO;
import com.example.movie.models.entities.CinemaDAO;
import com.example.movie.models.entities.RoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomDAO, Integer> {

    int countByCinemaDAO(CinemaDAO cinemaDAO);

    void deleteByCinemaDAO(CinemaDAO cinemaDAO);

    @Query(value = "select distinct room.* from room \n" +
            "inner join cinema on room.cinemaId = cinema.id \n" +
            "inner join schedule on schedule.roomId = room.id \n" +
            "inner join movie on movie.id = schedule.movieId \n" +
            "where movie.id = :movieId \n" +
            "and cinema.id = :cinemaId", nativeQuery = true)
    List<RoomDAO> findAllByMovieIdCinemaId(int movieId, int cinemaId);
}

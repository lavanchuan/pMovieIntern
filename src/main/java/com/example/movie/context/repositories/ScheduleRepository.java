package com.example.movie.context.repositories;

import com.example.movie.models.dto.ScheduleDTO;
import com.example.movie.models.entities.MovieDAO;
import com.example.movie.models.entities.RoomDAO;
import com.example.movie.models.entities.ScheduleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleDAO, Integer> {

    int countAllByRoomDAO(RoomDAO roomDAO);

    int countAllByMovieDAO(MovieDAO movieDAO);

    @Query(value = "select schedule.* from schedule \n" +
            "where year(startAt) = :year \n" +
            "and month(startAt) = :month \n" +
            "and day(startAt) = :day", nativeQuery = true)
    List<ScheduleDAO> findAllByStartDate(int year, int month, int day);

    @Query(value = "select schedule.* from schedule \n" +
            "where year(startAt) = :year \n" +
            "and month(startAt) = :month \n" +
            "and day(startAt) = :day " +
            "and roomId = :roomId", nativeQuery = true)
    List<ScheduleDAO> findAllByStartDateRoom(int year, int month, int day, int roomId);

    @Query(value = "select distinct schedule.id from schedule \n" +
            "where roomId = :roomId", nativeQuery = true)
    List<Integer> idListFromRoomId(int roomId);

    @Query(value = "select distinct schedule.* from schedule \n" +
            "inner join movie on movie.id = schedule.movieId \n" +
            "inner join room on room.id = schedule.roomId \n" +
            "where movie.id = :movieId \n" +
            "and room.id = :roomId", nativeQuery = true)
    List<ScheduleDAO> findAllByMovieIdRoomId(int movieId, int roomId);
}

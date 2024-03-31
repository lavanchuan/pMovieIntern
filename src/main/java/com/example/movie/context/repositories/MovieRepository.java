package com.example.movie.context.repositories;


import com.example.movie.models.entities.MovieDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieDAO, Integer> {

    @Query(value = "select movie.id from movie \n" +
            "inner join schedule on movie.id = schedule.movieId \n" +
            "inner join ticket on ticket.scheduleId = schedule.id \n" +
            "inner join billTicket on billTicket.ticketId = ticket.id \n" +
            "group by movie.id \n" +
            "order by sum(billTicket.quantity) desc", nativeQuery = true)
    List<Integer> featuredMovies();

//    @Query(value = "select movie.* from movie \n" +
//            "inner join schedule on movie.id = schedule.movieId \n" +
//            "inner join ticket on ticket.scheduleId = schedule.id \n" +
//            "inner join billTicket on billTicket.ticketId = ticket.id \n" +
//            "group by movie.id \n" +
//            "order by sum(billTicket.quantity) desc", nativeQuery = true)
//    List<MovieDAO> featuredMovieList();

    @Query(value = "select movie.* from movie \n" +
            "inner join schedule on movie.id = schedule.movieId \n" +
            "inner join ticket on ticket.scheduleId = schedule.id \n" +
            "inner join billTicket on billTicket.ticketId = ticket.id \n" +
            "group by movie.id \n" +
            "order by count(ticket.id) desc", nativeQuery = true)
    List<MovieDAO> featuredMovieList();


    @Query(value = "select distinct movie.id from movie \n" +
            "inner join schedule on movie.id = schedule.movieId \n" +
            "inner join room on schedule.roomId = room.id \n" +
            "inner join cinema on room.cinemaId = cinema.id \n" +
            "inner join seat on room.id = seat.roomId \n" +
            "inner join seatStatus on seat.seatStatusId = seatStatus.id \n" +
            "where nameOfCinema like %:nameCinema% \n" +
            "and room.name like %:nameRoom% \n" +
            "and seatStatus.nameStatus like %:seatStatus%", nativeQuery = true)
    List<Integer> moviesByCinemaRoomSeatstatus(String nameCinema, String nameRoom, String seatStatus);

    @Query(value = "select distinct movie.* from movie \n" +
            "inner join schedule on movie.id = schedule.movieId \n" +
            "inner join room on schedule.roomId = room.id \n" +
            "inner join cinema on room.cinemaId = cinema.id \n" +
            "inner join seat on room.id = seat.roomId \n" +
            "inner join seatStatus on seat.seatStatusId = seatStatus.id \n" +
            "where nameOfCinema like %:nameCinema% \n" +
            "and room.name like %:nameRoom% \n" +
            "and seatStatus.nameStatus like %:seatStatus%", nativeQuery = true)
    List<MovieDAO> movieListByCinemaRoomSeatstatus(String nameCinema, String nameRoom, String seatStatus);
}

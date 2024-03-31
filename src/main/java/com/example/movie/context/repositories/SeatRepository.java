package com.example.movie.context.repositories;

import com.example.movie.models.entities.RoomDAO;
import com.example.movie.models.entities.SeatDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<SeatDAO, Integer> {

    int countAllByRoomDAO(RoomDAO roomDAO);

    @Query(value = "select seat.roomId from seat \n" +
            "where seat.id = :seatId", nativeQuery = true)
    int roomIdFromSeatId(int seatId);
}

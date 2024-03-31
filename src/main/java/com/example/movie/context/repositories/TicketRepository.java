package com.example.movie.context.repositories;

import com.example.movie.models.entities.ScheduleDAO;
import com.example.movie.models.entities.SeatDAO;
import com.example.movie.models.entities.TicketDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketDAO, Integer> {

    int countAllBySeatDAO(SeatDAO seatDAO);

    int countAllByScheduleDAO(ScheduleDAO scheduleDAO);

    void deleteAllBySeatDAO(SeatDAO seatDAO);

    @Query(value = "select ticket.* from ticket \n" +
            "where scheduleId = :scheduleId \n" +
            "and isActive = true", nativeQuery = true)
    List<TicketDAO> findAllByScheduleIdAndTrueIsActive(int scheduleId);
}

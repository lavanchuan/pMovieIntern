package com.example.movie.context.repositories;

import com.example.movie.models.dto.IntervalDTO;
import com.example.movie.models.entities.BillDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillDAO, Integer> {

    @Query(value = "select bill.* from bill \n" +
            "inner join billStatus on billStatus.id = bill.id \n" +
            "where bill.customerId = :userId \n" +
            "and billStatus.name = 'UNPAID' \n" +
            "limit 1", nativeQuery = true)
    BillDAO findByUserIdAndNotPaid(int userId);

    @Query(value = "select count(DISTINCT bill.id) from bill \n" +
            "inner join billStatus on billStatus.id = bill.id \n" +
            "where bill.customerId = :userId \n" +
            "and billStatus.name = 'UNPAID'", nativeQuery = true)
    int countByUserIdAndunPaid(int userId);

    @Query(value = "select distinct bill.* from bill \n" +
            "inner join user on user.id = bill.customerId \n" +
            "where user.username like :username", nativeQuery = true)
    List<BillDAO> findAllByUsername(String username);

    @Query(value = "select concat(room.cinemaId , '---', \n" +
            "cinema.nameOfCinema, '---', \n" +
            "sum(billFood.quantity * food.price), '---', \n" +
            "sum(ticket.priceTicket), '---', \n" +
            "sum(billFood.quantity * food.price) + sum(ticket.priceTicket)) \n" +
            "from food \n" +
            "inner join billFood on food.id = billFood.foodId \n" +
            "inner join bill on bill.id = billFood.billId \n" +
            "inner join billTicket on billTicket.billId = bill.id \n" +
            "inner join ticket on ticket.id = billTicket.ticketId \n" +
            "inner join schedule on schedule.id = ticket.scheduleId \n" +
            "inner join room on room.id = schedule.roomId \n" +
            "inner join cinema on cinema.id = room.cinemaId \n" +
            "--where \n" +
            "group by room.cinemaId ", nativeQuery = true)
    List<String> revenueByInterval(int yearStart, int monthStart, int dayStart,
                                   int yearEnd, int monthEnd, int dayEnd);

    // FROM START DATE
    @Query(value = "select concat(room.cinemaId , '---', \n" +
            "cinema.nameOfCinema, '---', \n" +
            "sum(billFood.quantity * food.price), '---', \n" +
            "sum(ticket.priceTicket), '---', \n" +
            "sum(billFood.quantity * food.price) + sum(ticket.priceTicket)) \n" +
            "from food \n" +
            "inner join billFood on food.id = billFood.foodId \n" +
            "inner join bill on bill.id = billFood.billId \n" +
            "inner join billTicket on billTicket.billId = bill.id \n" +
            "inner join ticket on ticket.id = billTicket.ticketId \n" +
            "inner join schedule on schedule.id = ticket.scheduleId \n" +
            "inner join room on room.id = schedule.roomId \n" +
            "inner join cinema on cinema.id = room.cinemaId \n" +
            "where bill.createTime between :startDay \n" +
            "and :endDay \n" +
            "group by room.cinemaId ", nativeQuery = true)
    List<String> revenueByInterval(@Param(value = "startDay") LocalDate startDay,
                                   @Param(value = "endDay") LocalDate endDay);

    @Query(value = "select concat(room.cinemaId , '---', \n" +
            "cinema.nameOfCinema, '---', \n" +
            "sum(billFood.quantity * food.price), '---', \n" +
            "sum(ticket.priceTicket), '---', \n" +
            "sum(billFood.quantity * food.price) + sum(ticket.priceTicket)) \n" +
            "from food \n" +
            "inner join billFood on food.id = billFood.foodId \n" +
            "inner join bill on bill.id = billFood.billId \n" +
            "inner join billTicket on billTicket.billId = bill.id \n" +
            "inner join ticket on ticket.id = billTicket.ticketId \n" +
            "inner join schedule on schedule.id = ticket.scheduleId \n" +
            "inner join room on room.id = schedule.roomId \n" +
            "inner join cinema on cinema.id = room.cinemaId \n" +
            "group by room.cinemaId ", nativeQuery = true)
    List<String> revenueByInterval();


    @Query(value = "select count(bill.id) from bill \n" +
            "where bill.customerId = :userId", nativeQuery = true)
    int countByUserId(@Param(value = "userId") int userId);
}

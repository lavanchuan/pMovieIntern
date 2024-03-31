package com.example.movie.context.repositories;

import com.example.movie.models.entities.BillTicketDAO;
import com.example.movie.models.entities.TicketDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillTicketRepository extends JpaRepository<BillTicketDAO, Integer> {

    int countAllByTicketDAO(TicketDAO ticketDAO);
}

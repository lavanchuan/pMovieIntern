package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.TicketDTO;
import com.example.movie.models.entities.TicketDAO;
import com.example.movie.models.mapper.TicketMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements ICRUDService<TicketDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    TicketMapper ticketMapper;

    @Override
    public boolean add(TicketDTO ticketDTO) {

        dbContext.ticketRepository.save(ticketMapper.toEntity(ticketDTO, dbContext));

        return true;
    }

    @Override
    public boolean update(TicketDTO ticketDTO) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {

        if(dbContext.ticketRepository.existsById(id)){
            TicketDAO ticketDAO = dbContext.ticketRepository.findById(id).orElseThrow();
            if(dbContext.billTicketRepository.countAllByTicketDAO(ticketDAO) == 0){
                dbContext.ticketRepository.deleteById(id);
                return true;
            }
        }

        return false;
    }
}

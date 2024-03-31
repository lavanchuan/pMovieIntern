package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.SeatDTO;
import com.example.movie.models.entities.SeatDAO;
import com.example.movie.models.entities.TicketDAO;
import com.example.movie.models.mapper.SeatMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements ICRUDService<SeatDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    SeatMapper seatMapper;

    @Override
    public boolean add(SeatDTO seatDTO) {
        dbContext.seatRepository.save(seatMapper.toEntity(seatDTO, dbContext));
        return true;
    }

    @Override
    public boolean update(SeatDTO seatDTO) {
        if(seatDTO.getId() > 0 && dbContext.seatRepository.existsById(seatDTO.getId())){
            dbContext.seatRepository.save(seatMapper.toEntity(seatDTO, dbContext));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if(dbContext.seatRepository.existsById(id)){
            SeatDAO seatDAO = dbContext.seatRepository.findById(id).orElseThrow();
            if(dbContext.ticketRepository.countAllBySeatDAO(seatDAO) == 0){
                dbContext.seatRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

//    public void delete(Integer id, boolean allowDeleteDependency){
//        if(dbContext.seatRepository.existsById(id)){
//            SeatDAO seatDAO = dbContext.seatRepository.findById(id).orElseThrow();
//            if(dbContext.ticketRepository.countAllBySeatDAO(seatDAO) == 0){
//                dbContext.seatRepository.deleteById(id);
//            } else
//                // check allow delete ticket by seat id
//            if(allowDeleteTicket){
//                dbContext.ticketRepository.deleteAllBySeatDAO(seatDAO);
//                dbContext.seatRepository.deleteById(id);
//            }
//        }
//    }
}

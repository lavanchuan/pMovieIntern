package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.RoomDTO;
import com.example.movie.models.entities.RoomDAO;
import com.example.movie.models.mapper.RoomMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements ICRUDService<RoomDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    RoomMapper roomMapper;


    @Override
    public boolean add(RoomDTO roomDTO) {
        dbContext.roomRepository.save(roomMapper.toEntity(roomDTO, dbContext));
        return true;
    }

    @Override
    public boolean update(RoomDTO roomDTO) {
        if(roomDTO.getId() > 0 && dbContext.roomRepository.existsById(roomDTO.getId())){

            dbContext.roomRepository.save(roomMapper.toEntity(roomDTO, dbContext));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if(dbContext.roomRepository.existsById(id)){
            RoomDAO roomDAO = dbContext.roomRepository.findById(id).orElseThrow();
            if(dbContext.scheduleRepository.countAllByRoomDAO(roomDAO) == 0
            && dbContext.seatRepository.countAllByRoomDAO(roomDAO) == 0){
                dbContext.roomRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public List<RoomDTO> findAllByMovieIdCinemaId(int movieId, int cinemaId) {
        return dbContext.roomRepository.findAllByMovieIdCinemaId(movieId, cinemaId)
                .stream().map(roomMapper::toDTO).toList();
    }
}

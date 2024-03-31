package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.RoomType;
import com.example.movie.models.dto.RoomDTO;
import com.example.movie.models.entities.RoomDAO;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper implements BaseMapper<RoomDAO, RoomDTO, DbContext>{
    @Override
    public RoomDAO toEntity(RoomDTO roomDTO, DbContext dbContext) {

        RoomDAO roomDAO = new RoomDAO();

        if(roomDTO.getId() > 0 && dbContext.roomRepository.existsById(roomDTO.getId())){
            // update
            roomDAO = dbContext.roomRepository.findById(roomDTO.getId()).orElseThrow();

            if(roomDTO.getCapacity() > 0) roomDAO.setCapacity(roomDTO.getCapacity());
            if(roomDTO.getType() != null) roomDAO.setType(roomDTO.getType());
            if(roomDTO.getDescription() != null && !roomDTO.getDescription().isEmpty()) roomDAO.setDescription(roomDTO.getDescription());
            if(roomDTO.getName() != null && !roomDTO.getName().isEmpty()) roomDAO.setName(roomDTO.getName());
            if(roomDTO.getCode() != null && !roomDTO.getCode().isEmpty()) roomDAO.setCode(roomDTO.getCode());
            roomDAO.setActive(roomDTO.isActive());
            if(roomDTO.getCinemaId() > 0 && dbContext.cinemaRepository.existsById(roomDTO.getCinemaId())){
                roomDAO.setCinemaDAO(dbContext.cinemaRepository.findById(roomDTO.getCinemaId()).orElseThrow());
            }
        } else {
            // add

            roomDAO.setCode(roomDTO.getCode());
            roomDAO.setName(roomDTO.getName());
            roomDAO.setDescription(roomDTO.getDescription());
            roomDAO.setCapacity(roomDTO.getCapacity());
            if(roomDTO.getType() != null) roomDAO.setType(roomDTO.getType());
            else roomDAO.setType(RoomType.PUBLIC);
            roomDAO.setActive(roomDTO.isActive());
            if(roomDTO.getCinemaId() > 0 && dbContext.cinemaRepository.existsById(roomDTO.getCinemaId())){
                roomDAO.setCinemaDAO(dbContext.cinemaRepository.findById(roomDTO.getCinemaId()).orElseThrow());
            }
        }

        return roomDAO;
    }

    @Override
    public RoomDTO toDTO(RoomDAO roomDAO) {

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(roomDAO.getId());
        roomDTO.setCapacity(roomDAO.getCapacity());
        roomDTO.setType(roomDAO.getType());
        roomDTO.setCode(roomDAO.getCode());
        roomDTO.setName(roomDAO.getName());
        roomDTO.setDescription(roomDAO.getDescription());
        roomDTO.setActive(roomDAO.isActive());
        if(roomDAO.getCinemaDAO() != null) {
            roomDTO.setCinemaId(roomDAO.getCinemaDAO().getId());
        }

        return roomDTO;
    }
}

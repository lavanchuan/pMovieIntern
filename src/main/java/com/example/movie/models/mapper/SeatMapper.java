package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.SeatDTO;
import com.example.movie.models.entities.SeatDAO;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper implements BaseMapper<SeatDAO, SeatDTO, DbContext> {
    @Override
    public SeatDAO toEntity(SeatDTO seatDTO, DbContext dbContext) {

        SeatDAO seatDAO = new SeatDAO();

        if(seatDTO.getId() > 0 && dbContext.seatRepository.existsById(seatDTO.getId())){
            // update

            seatDAO = dbContext.seatRepository.findById(seatDTO.getId()).orElseThrow();

            if(seatDTO.getNumber() > 0) seatDAO.setNumber(seatDTO.getNumber());
            if(seatDTO.getLine() != null && !seatDTO.getLine().isEmpty()) seatDAO.setLine(seatDTO.getLine());
            seatDAO.setActive(seatDTO.isActive());

            if(dbContext.roomRepository.existsById(seatDTO.getRoomId())){
                seatDAO.setRoomDAO(dbContext.roomRepository.findById(seatDTO.getRoomId()).orElseThrow());
            }

            if(dbContext.seatStatusRepository.existsById(seatDTO.getSeatStatusId())){
                seatDAO.setSeatStatusDAO(dbContext.seatStatusRepository.findById(seatDTO.getSeatStatusId()).orElseThrow());
            }

            if(dbContext.seatTypeRepository.existsById(seatDTO.getSeatTypeId())){
                seatDAO.setSeatTypeDAO(dbContext.seatTypeRepository.findById(seatDTO.getSeatTypeId()).orElseThrow());
            }

        } else {
            // add
            seatDAO.setNumber(seatDTO.getNumber());
            seatDAO.setActive(seatDTO.isActive());
            seatDAO.setLine(seatDTO.getLine());

            if(dbContext.roomRepository.existsById(seatDTO.getRoomId())){
                seatDAO.setRoomDAO(dbContext.roomRepository.findById(seatDTO.getRoomId()).orElseThrow());
            }

            if(dbContext.seatStatusRepository.existsById(seatDTO.getSeatStatusId())){
                seatDAO.setSeatStatusDAO(dbContext.seatStatusRepository.findById(seatDTO.getSeatStatusId()).orElseThrow());
            }

            if(dbContext.seatTypeRepository.existsById(seatDTO.getSeatTypeId())){
                seatDAO.setSeatTypeDAO(dbContext.seatTypeRepository.findById(seatDTO.getSeatTypeId()).orElseThrow());
            }


        }

        return seatDAO;
    }

    @Override
    public SeatDTO toDTO(SeatDAO seatDAO) {

        SeatDTO seatDTO = new SeatDTO();

        seatDTO.setId(seatDAO.getId());
        seatDTO.setNumber(seatDAO.getNumber());
        if(seatDAO.getSeatStatusDAO() != null) {
            seatDTO.setSeatStatusId(seatDAO.getSeatStatusDAO().getId());
        }
        if(seatDAO.getRoomDAO() != null) {
            seatDTO.setRoomId(seatDAO.getRoomDAO().getId());
        }
        if(seatDAO.getSeatTypeDAO() != null) {
            seatDTO.setSeatTypeId(seatDAO.getSeatTypeDAO().getId());
        }

        seatDTO.setLine(seatDAO.getLine());

        seatDTO.setActive(seatDAO.isActive());

        return seatDTO;
    }
}

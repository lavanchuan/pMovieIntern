package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.ScheduleDTO;
import com.example.movie.models.entities.ScheduleDAO;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper implements BaseMapper<ScheduleDAO, ScheduleDTO, DbContext>{
    @Override
    public ScheduleDAO toEntity(ScheduleDTO scheduleDTO, DbContext dbContext) {

        ScheduleDAO scheduleDAO = new ScheduleDAO();

        if(scheduleDTO.getId() > 0 && dbContext.scheduleRepository.existsById(scheduleDTO.getId())){
            // update
            scheduleDAO = dbContext.scheduleRepository.findById(scheduleDTO.getId()).orElseThrow();
            if(scheduleDTO.getPrice() > 0) scheduleDAO.setPrice(scheduleDTO.getPrice());
            if(scheduleDTO.getStartAt() != null) scheduleDAO.setStartAt(scheduleDTO.getStartAt());
            if(scheduleDTO.getEndAt() != null) scheduleDAO.setEndAt(scheduleDTO.getEndAt());
            if(scheduleDTO.getCode() != null && !scheduleDTO.getCode().isEmpty()) scheduleDAO.setCode(scheduleDTO.getCode());
            if(scheduleDTO.getName() != null && !scheduleDTO.getName().isEmpty()) scheduleDAO.setName(scheduleDTO.getName());
        } else {
            // add
            scheduleDAO.setPrice(scheduleDTO.getPrice());
            scheduleDAO.setStartAt(scheduleDTO.getStartAt());
            scheduleDAO.setEndAt(scheduleDTO.getEndAt());
            scheduleDAO.setCode(scheduleDTO.getCode());
            scheduleDAO.setName(scheduleDTO.getName());
        }

        if (scheduleDTO.getMovieId() > 0
                && dbContext.movieRepository.existsById(scheduleDTO.getMovieId())) {
            scheduleDAO.setMovieDAO(dbContext.movieRepository.findById(scheduleDTO.getMovieId()).orElseThrow());
        }
        if (scheduleDTO.getRoomId() > 0 &&
                dbContext.roomRepository.existsById(scheduleDTO.getRoomId())) {
            scheduleDAO.setRoomDAO(dbContext.roomRepository.findById(scheduleDTO.getRoomId()).orElseThrow());
        }
        scheduleDAO.setActive(scheduleDTO.isActive());

        return scheduleDAO;
    }

    @Override
    public ScheduleDTO toDTO(ScheduleDAO scheduleDAO) {

        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(scheduleDAO.getId());
        scheduleDTO.setPrice(scheduleDAO.getPrice());
        scheduleDTO.setStartAt(scheduleDAO.getStartAt());
        scheduleDTO.setEndAt(scheduleDAO.getEndAt());
        scheduleDTO.setCode(scheduleDAO.getCode());
        if(scheduleDAO.getMovieDAO() != null) scheduleDTO.setMovieId(scheduleDAO.getMovieDAO().getId());
        scheduleDTO.setName(scheduleDAO.getName());
        if(scheduleDAO.getRoomDAO() != null) scheduleDTO.setRoomId(scheduleDAO.getRoomDAO().getId());
        scheduleDTO.setActive(scheduleDAO.isActive());

        return scheduleDTO;
    }
}

package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.ScheduleDTO;
import com.example.movie.models.entities.ScheduleDAO;
import com.example.movie.models.mapper.ScheduleMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ICRUDService<ScheduleDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public boolean add(ScheduleDTO scheduleDTO) {

        List<ScheduleDAO> scheduleDAOS = dbContext.scheduleRepository
                .findAllByStartDateRoom(scheduleDTO.getStartAt().getYear(),
                        scheduleDTO.getStartAt().getMonthValue(),
                        scheduleDTO.getStartAt().getDayOfMonth(),
                        scheduleDTO.getRoomId());
        System.out.println("SCHEDULE: " + scheduleDAOS.size());

        // check and update endAt
        if(scheduleDTO.getEndAt() == null){
            if(scheduleDTO.getMovieId() > 0 && dbContext.movieRepository.existsById(scheduleDTO.getMovieId())){
                int durationMinutes = dbContext.movieRepository.findById(scheduleDTO.getMovieId()).orElseThrow()
                                .getMovieDuration();
                scheduleDTO.setEndAt(scheduleDTO.getStartAt().plusMinutes(durationMinutes));
            } else {
                // endAt = startAt
                scheduleDTO.setEndAt(scheduleDTO.getStartAt());
            }
        }

        if(scheduleDAOS.size() != 0){
            boolean isValidSchedule = isValidSchedule(scheduleDTO, scheduleDAOS);
            System.out.println("Schedule time is valid: " + isValidSchedule);

            if(!isValidSchedule) return false;
        }

        dbContext.scheduleRepository.save(scheduleMapper.toEntity(scheduleDTO, dbContext));

        return true;
    }

    private boolean isValidSchedule(ScheduleDTO scheduleDTO, List<ScheduleDAO> scheduleDAOS) {

        for(ScheduleDAO scheduleDAO : scheduleDAOS){
            if(!isNonOverlapIntervals(scheduleDAO.getStartAt(), scheduleDAO.getEndAt(),
                    scheduleDTO.getStartAt(), scheduleDTO.getEndAt()))
                return false;
        }
        return true;
    }

    @Override
    public boolean update(ScheduleDTO scheduleDTO) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if(dbContext.scheduleRepository.existsById(id)){
            ScheduleDAO scheduleDAO = dbContext.scheduleRepository.findById(id).orElseThrow();
            if(dbContext.ticketRepository.countAllByScheduleDAO(scheduleDAO) == 0){
                dbContext.scheduleRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public boolean isNonOverlapIntervals(LocalDateTime a1, LocalDateTime b1, LocalDateTime a2, LocalDateTime b2){
        return (a1.isBefore(a2) && b1.isBefore(a2)) || (a2.isBefore(a1) && b2.isBefore(a1));
    }

    public List<ScheduleDTO> findAllByMovieIdRoomId(int movieId, int roomId) {
        return dbContext.scheduleRepository.findAllByMovieIdRoomId(movieId, roomId)
                .stream().map(scheduleMapper::toDTO).toList();
    }
}

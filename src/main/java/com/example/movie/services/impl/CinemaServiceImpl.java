package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.CinemaDTO;
import com.example.movie.models.entities.CinemaDAO;
import com.example.movie.models.entities.MovieDAO;
import com.example.movie.models.mapper.CinemaMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaServiceImpl implements ICRUDService<CinemaDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    CinemaMapper cinemaMapper;

    @Override
    public boolean add(CinemaDTO cinemaDTO) {
        dbContext.cinemaRepository.save(cinemaMapper.toEntity(cinemaDTO, dbContext));
        return true;
    }

    @Override
    public boolean update(CinemaDTO cinemaDTO) {
        if(cinemaDTO.getId() > 0 && dbContext.cinemaRepository.existsById(cinemaDTO.getId())){
            dbContext.cinemaRepository.save(cinemaMapper.toEntity(cinemaDTO, dbContext));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if(dbContext.cinemaRepository.existsById(id)){
            CinemaDAO cinemaDAO = dbContext.cinemaRepository.findById(id).orElseThrow();
            if(dbContext.roomRepository.countByCinemaDAO(cinemaDAO) == 0){
                dbContext.cinemaRepository.deleteById(id);
                return true;
            }

        }
        return false;
    }

//    public void delete(Integer id, boolean allowDeleteRoom){
//
//        if(dbContext.cinemaRepository.existsById(id)){
//            CinemaDAO cinemaDAO = dbContext.cinemaRepository.findById(id).orElseThrow();
//            if(dbContext.roomRepository.countByCinemaDAO(cinemaDAO) == 0){
//                dbContext.cinemaRepository.deleteById(id);
//            } else
//            // confirm delete room use cinema id
//            if(allowDeleteRoom){
//                deleteRoomByCinema(cinemaDAO);
//                dbContext.cinemaRepository.deleteById(id);
//            }
//        }
//    }


//    public void deleteRoomByCinema(CinemaDAO cinemaDAO){
//        dbContext.roomRepository.deleteByCinemaDAO(cinemaDAO);
//    }

    public List<CinemaDTO> findAllByMovieId(int movieId){
        return dbContext.cinemaRepository.findAllByMovieId(movieId).stream()
                .map(cinemaMapper::toDTO).toList();
    }
}

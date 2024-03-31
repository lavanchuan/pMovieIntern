package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.MovieDTO;
import com.example.movie.models.entities.MovieDAO;
import com.example.movie.models.mapper.MovieMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements ICRUDService<MovieDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    MovieMapper movieMapper;

    @Override
    public boolean add(MovieDTO movieDTO) {
        dbContext.movieRepository.save(movieMapper.toEntity(movieDTO, dbContext));
        return true;
    }

    @Override
    public boolean update(MovieDTO movieDTO) {
        if(movieDTO.getId() > 0 && dbContext.movieRepository.existsById(movieDTO.getId())){
            dbContext.movieRepository.save(movieMapper.toEntity(movieDTO, dbContext));
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if(id > 0 && dbContext.movieRepository.existsById(id)){
            MovieDAO movieDAO = dbContext.movieRepository.findById(id).orElseThrow();
            if(dbContext.scheduleRepository.countAllByMovieDAO(movieDAO) == 0){
                dbContext.movieRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public List<MovieDTO> featuredMovies(){
        List<MovieDTO> movieDTOS = dbContext.movieRepository.featuredMovieList()
                .stream().map(movieMapper::toDTO).toList();

        return movieDTOS;
    }

    public List<MovieDTO> findAll(){
        return dbContext.movieRepository.findAll().stream()
                .map(movieMapper::toDTO).toList();
    }
}

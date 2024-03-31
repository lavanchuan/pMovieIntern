package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.MovieDTO;
import com.example.movie.models.entities.MovieDAO;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements BaseMapper<MovieDAO, MovieDTO, DbContext> {


    @Override
    public MovieDAO toEntity(MovieDTO movieDTO, DbContext dbContext) {

        MovieDAO movieDAO = new MovieDAO();

        if(movieDTO.getId() > 0 && dbContext.movieRepository.existsById(movieDTO.getId())){
            // update

            movieDAO = dbContext.movieRepository.findById(movieDTO.getId()).orElseThrow();

            if(movieDTO.getMovieDuration() > 0) movieDAO.setMovieDuration(movieDTO.getMovieDuration());

            if(movieDTO.getEndTime() != null) movieDAO.setEndTime(movieDTO.getEndTime());
            if(movieDTO.getPremiereDate() != null) movieDAO.setPremiereDate(movieDTO.getEndTime());

            if(movieDTO.getDescription() != null && !movieDTO.getDescription().isEmpty())
                movieDAO.setDescription(movieDTO.getDescription());
            if(movieDTO.getDirector() != null && !movieDTO.getDirector().isEmpty())
                movieDAO.setDirector(movieDTO.getDirector());
            if(movieDTO.getImage() != null && !movieDTO.getImage().isEmpty())
                movieDAO.setImage(movieDTO.getImage());
            if(movieDTO.getHeroImage() != null && !movieDTO.getHeroImage().isEmpty())
                movieDAO.setHeroImage(movieDTO.getHeroImage());
            if(movieDTO.getLanguage() != null && !movieDTO.getLanguage().isEmpty())
                movieDAO.setLanguage(movieDTO.getLanguage());
            if(movieDTO.getName() != null && !movieDTO.getName().isEmpty())
                movieDAO.setName(movieDTO.getName());

            movieDAO.setActive(movieDTO.isActive());

            if(movieDTO.getMovieTypeId() > 0 && dbContext.movieTypeRepository.existsById(movieDTO.getMovieTypeId())){
                movieDAO.setMovieTypeDAO(dbContext.movieTypeRepository.findById(movieDTO.getMovieTypeId()).orElseThrow());
            }

            if(movieDTO.getRateId() > 0 && dbContext.rateRepository.existsById(movieDTO.getRateId())){
                movieDAO.setRateDAO(dbContext.rateRepository.findById(movieDTO.getRateId()).orElseThrow());
            }

        } else {
            // add
            movieDAO.setMovieDuration(movieDTO.getMovieDuration());
            movieDAO.setEndTime(movieDTO.getEndTime());
            movieDAO.setPremiereDate(movieDTO.getPremiereDate());
            movieDAO.setDescription(movieDTO.getDescription());
            movieDAO.setDirector(movieDTO.getDirector());
            movieDAO.setImage(movieDTO.getImage());
            movieDAO.setHeroImage(movieDTO.getHeroImage());
            movieDAO.setLanguage(movieDTO.getLanguage());
            movieDAO.setName(movieDTO.getName());
            movieDAO.setTrailer(movieDTO.getTrailer());
            movieDAO.setActive(movieDTO.isActive());

            if(movieDTO.getMovieTypeId() > 0 && dbContext.movieTypeRepository.existsById(movieDTO.getMovieTypeId())){
                movieDAO.setMovieTypeDAO(dbContext.movieTypeRepository.findById(movieDTO.getMovieTypeId()).orElseThrow());
            }

            if(movieDTO.getRateId() > 0 && dbContext.rateRepository.existsById(movieDTO.getRateId())){
                movieDAO.setRateDAO(dbContext.rateRepository.findById(movieDTO.getRateId()).orElseThrow());
            }

        }

        return movieDAO;
    }

    @Override
    public MovieDTO toDTO(MovieDAO movieDAO) {

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movieDAO.getId());
        movieDTO.setMovieDuration(movieDAO.getMovieDuration());
        movieDTO.setEndTime(movieDAO.getEndTime());
        movieDTO.setPremiereDate(movieDAO.getPremiereDate());
        movieDTO.setDescription(movieDAO.getDescription());
        movieDTO.setDirector(movieDAO.getDirector());
        movieDTO.setImage(movieDAO.getImage());
        movieDTO.setHeroImage(movieDAO.getHeroImage());
        movieDTO.setLanguage(movieDAO.getLanguage());
        movieDTO.setName(movieDAO.getName());
        movieDTO.setTrailer(movieDAO.getTrailer());
        movieDTO.setActive(movieDAO.isActive());

        if(movieDAO.getMovieTypeDAO() != null){
            movieDTO.setMovieTypeId(movieDAO.getMovieTypeDAO().getId());
        }

        if(movieDAO.getRateDAO() != null){
            movieDTO.setRateId(movieDAO.getRateDAO().getId());
        }

        return movieDTO;
    }
}

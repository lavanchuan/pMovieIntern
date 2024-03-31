package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.CinemaDTO;
import com.example.movie.models.entities.CinemaDAO;
import org.springframework.stereotype.Component;

@Component
public class CinemaMapper implements BaseMapper<CinemaDAO, CinemaDTO, DbContext> {
    @Override
    public CinemaDAO toEntity(CinemaDTO cinemaDTO, DbContext dbContext) {

        CinemaDAO cinemaDAO = new CinemaDAO();

        if(cinemaDTO.getId() > 0 && dbContext.cinemaRepository.existsById(cinemaDTO.getId())){ // update
            cinemaDAO = dbContext.cinemaRepository.findById(cinemaDTO.getId())
                    .orElseThrow();//

            if(cinemaDTO.getCode() != null && !cinemaDTO.getCode().isEmpty()) cinemaDAO.setCode(cinemaDTO.getCode());
            if(cinemaDTO.getAddress() != null && !cinemaDTO.getAddress().isEmpty()) cinemaDAO.setAddress(cinemaDTO.getAddress());
            if(cinemaDTO.getDescription() != null && !cinemaDTO.getDescription().isEmpty()) cinemaDAO.setDescription(cinemaDTO.getDescription());
            if(cinemaDTO.getNameOfCinema() != null && !cinemaDTO.getNameOfCinema().isEmpty()) cinemaDAO.setNameOfCinema(cinemaDTO.getNameOfCinema());

            // ???
            cinemaDAO.setActive(cinemaDTO.isActive());

        } else { // add
            cinemaDAO.setCode(cinemaDTO.getCode());
            cinemaDAO.setAddress(cinemaDTO.getAddress());
            cinemaDAO.setDescription(cinemaDTO.getDescription());
            cinemaDAO.setNameOfCinema(cinemaDTO.getNameOfCinema());
            cinemaDAO.setActive(cinemaDTO.isActive());
        }

        return cinemaDAO;
    }

    @Override
    public CinemaDTO toDTO(CinemaDAO cinemaDAO) {

        CinemaDTO cinemaDTO = new CinemaDTO();

        cinemaDTO.setId(cinemaDAO.getId());
        cinemaDTO.setCode(cinemaDAO.getCode());
        cinemaDTO.setAddress(cinemaDAO.getAddress());
        cinemaDTO.setNameOfCinema(cinemaDAO.getNameOfCinema());
        cinemaDTO.setDescription(cinemaDAO.getDescription());
        cinemaDTO.setActive(cinemaDAO.isActive());

        return cinemaDTO;
    }
}

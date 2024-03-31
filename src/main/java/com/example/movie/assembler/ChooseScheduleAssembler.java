package com.example.movie.assembler;

import com.example.movie.controllers.ApiController;
import com.example.movie.models.dto.RoomDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ChooseScheduleAssembler implements RepresentationModelAssembler<RoomDTO, EntityModel<RoomDTO>> {

    private int movieId;

    public void setMovieId(int movieId){
        this.movieId = movieId;
    }

    @Override
    public EntityModel<RoomDTO> toModel(RoomDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ApiController.class).chooseSchedule(movieId, entity.getId())).withRel("Choose Schedule"));
    }
}

package com.example.movie.assembler;

import com.example.movie.controllers.ApiController;
import com.example.movie.models.dto.ScheduleDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ChooseFoodAssembler implements RepresentationModelAssembler<ScheduleDTO, EntityModel<ScheduleDTO>> {

    @Override
    public EntityModel<ScheduleDTO> toModel(ScheduleDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ApiController.class).chooseFood(entity.getId())).withRel("Choose Food"));
    }
}

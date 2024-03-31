package com.example.movie.assembler;

import com.example.movie.controllers.ApiController;
import com.example.movie.models.dto.MovieDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ChooseCiemaAssembler implements RepresentationModelAssembler<MovieDTO, EntityModel<MovieDTO>> {
    @Override
    public EntityModel<MovieDTO> toModel(MovieDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ApiController.class).chooseCinema(entity.getId())).withRel("Choose Cinema"));
    }

//    @Override
//    public CollectionModel<EntityModel<MovieDTO>> toCollectionModel(Iterable<? extends MovieDTO> entities) {
//        return RepresentationModelAssembler.super.toCollectionModel(entities);
//    }
}

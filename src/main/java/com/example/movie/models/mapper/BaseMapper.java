package com.example.movie.models.mapper;

public interface BaseMapper<ENTITY, DTO, DBCONTEXT> {
    ENTITY toEntity(DTO dto, DBCONTEXT dbcontext);
    DTO toDTO(ENTITY entity);
}

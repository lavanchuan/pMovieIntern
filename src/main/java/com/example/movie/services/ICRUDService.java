package com.example.movie.services;

public interface ICRUDService<DTO, ID> {

    boolean add(DTO dto);

    boolean update(DTO dto);

    boolean delete(ID id);
}

package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.BillTicketDTO;
import com.example.movie.models.mapper.BillticketMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillTicketServiceImpl implements ICRUDService<BillTicketDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    BillticketMapper billTicketMapper;

    @Override
    public boolean add(BillTicketDTO billTicketDTO) {

        dbContext.billTicketRepository.save(billTicketMapper.toEntity(billTicketDTO, dbContext));

        return true;
    }

    @Override
    public boolean update(BillTicketDTO billTicketDTO) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}

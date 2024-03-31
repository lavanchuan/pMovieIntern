package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.RankCustomerDTO;
import com.example.movie.models.mapper.RankCustomerMapper;
import com.example.movie.services.AdminService;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankCustomerServiceImpl implements ICRUDService<RankCustomerDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    RankCustomerMapper rankCustomerMapper;

    @Autowired
    PromotionServiceImpl promotionService;

    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean add(RankCustomerDTO rankCustomerDTO) {
        if(dbContext.rankCustomerRepository.existsById(rankCustomerDTO.getId())) return false;
        dbContext.rankCustomerRepository.save(rankCustomerMapper.toEntity(rankCustomerDTO, dbContext));
        return true;
    }

    @Override
    public boolean update(RankCustomerDTO rankCustomerDTO) {
        if(!dbContext.rankCustomerRepository.existsById(rankCustomerDTO.getId())) return false;
        dbContext.rankCustomerRepository.save(rankCustomerMapper.toEntity(rankCustomerDTO, dbContext));
        return true;
    }

    @Override
    public boolean delete(Integer id) {

        if(dbContext.rankCustomerRepository.existsById(id)){
            if(promotionService.countByRankCustomerId(id) == 0
            && userService.countByRankCustomerId(id) == 0)
                return true;
        }

        return false;
    }
}

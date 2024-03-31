package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.PromotionDTO;
import com.example.movie.models.mapper.PromotionMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements ICRUDService<PromotionDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    PromotionMapper promotionMapper;

    @Override
    public boolean add(PromotionDTO promotionDTO) {
        if(dbContext.promotionRepository.existsById(promotionDTO.getId())) return false;

        dbContext.promotionRepository.save(promotionMapper.toEntity(promotionDTO, dbContext));

        return true;
    }

    @Override
    public boolean update(PromotionDTO promotionDTO) {

        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    public int countByRankCustomerId(int rankCustomerId){
        return dbContext.promotionRepository.countByRankCustomerId(rankCustomerId);
    }
}

package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.PromotionDTO;
import com.example.movie.models.entities.PromotionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper implements BaseMapper<PromotionDAO, PromotionDTO, DbContext>{

    @Override
    public PromotionDAO toEntity(PromotionDTO promotionDTO, DbContext dbContext) {

        PromotionDAO promotionDAO = new PromotionDAO();

        if(promotionDTO.getId() > 0 && dbContext.promotionRepository.existsById(promotionDTO.getId())){
            // update
            promotionDAO = dbContext.promotionRepository.findById(promotionDTO.getId()).orElseThrow();

            if(promotionDTO.getPercent() > 0) promotionDAO.setPercent(promotionDTO.getPercent());
            if(promotionDTO.getQuantity() > 0) promotionDAO.setQuantity(promotionDTO.getQuantity());
            if(promotionDTO.getType() != null && !promotionDTO.getType().isEmpty()) promotionDAO.setType(promotionDTO.getType());
            if(promotionDTO.getDescription() != null && !promotionDTO.getDescription().isEmpty()) promotionDAO.setDescription(promotionDTO.getDescription());
            if(promotionDTO.getName() != null && !promotionDTO.getName().isEmpty()) promotionDAO.setName(promotionDTO.getName());
            if(promotionDTO.getStartTime() != null) promotionDAO.setStartTime(promotionDTO.getStartTime());
            if(promotionDTO.getEndTime() != null) promotionDAO.setEndTime(promotionDTO.getEndTime());
        } else {
            // add
            promotionDAO.setPercent(promotionDTO.getPercent());
            promotionDAO.setQuantity(promotionDTO.getQuantity());
            promotionDAO.setType(promotionDTO.getType());
            promotionDAO.setStartTime(promotionDTO.getStartTime());
            promotionDAO.setEndTime(promotionDTO.getEndTime());
            promotionDAO.setDescription(promotionDTO.getDescription());
            promotionDAO.setName(promotionDTO.getName());
        }

        promotionDAO.setActive(promotionDTO.isActive());

        if(promotionDTO.getRankCustomerId() > 0 && dbContext.rankCustomerRepository.existsById(promotionDTO.getRankCustomerId())){
            promotionDAO.setRankCustomerDAO(dbContext.rankCustomerRepository.findById(promotionDTO.getRankCustomerId()).orElseThrow());
        }

        return promotionDAO;
    }

    @Override
    public PromotionDTO toDTO(PromotionDAO promotionDAO) {

        PromotionDTO promotionDTO = new PromotionDTO();

        promotionDTO.setId(promotionDAO.getId());
        promotionDTO.setPercent(promotionDAO.getPercent());
        promotionDTO.setQuantity(promotionDAO.getQuantity());
        promotionDTO.setType(promotionDAO.getType());
        promotionDTO.setStartTime(promotionDAO.getStartTime());
        promotionDTO.setEndTime(promotionDAO.getEndTime());
        promotionDTO.setDescription(promotionDAO.getDescription());
        promotionDTO.setName(promotionDAO.getName());
        promotionDTO.setActive(promotionDAO.isActive());
        if(promotionDAO.getRankCustomerDAO() != null) promotionDTO.setRankCustomerId(promotionDAO.getRankCustomerDAO().getId());

        return promotionDTO;
    }
}

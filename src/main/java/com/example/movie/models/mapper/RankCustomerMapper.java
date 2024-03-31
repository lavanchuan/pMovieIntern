package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.RankCustomerDTO;
import com.example.movie.models.entities.RankCustomerDAO;
import org.springframework.stereotype.Component;

@Component
public class RankCustomerMapper implements BaseMapper<RankCustomerDAO, RankCustomerDTO, DbContext>{

    @Override
    public RankCustomerDAO toEntity(RankCustomerDTO rankCustomerDTO, DbContext dbContext) {
        RankCustomerDAO rankCustomerDAO = new RankCustomerDAO();

        if(rankCustomerDTO.getId() > 0 && dbContext.rankCustomerRepository.existsById(rankCustomerDTO.getId())){
            // update
            rankCustomerDAO = dbContext.rankCustomerRepository.findById(rankCustomerDTO.getId()).orElseThrow();

            if(rankCustomerDTO.getPoint() > 0) rankCustomerDAO.setPoint(rankCustomerDTO.getPoint());
            if(rankCustomerDTO.getDescription() != null && !rankCustomerDTO.getDescription().isEmpty()) rankCustomerDAO.setDescription(rankCustomerDTO.getDescription());
            if(rankCustomerDTO.getName() != null && !rankCustomerDTO.getName().isEmpty()) rankCustomerDAO.setName(rankCustomerDTO.getName());

        } else {
            // add
            rankCustomerDAO.setPoint(rankCustomerDTO.getPoint());
            rankCustomerDAO.setDescription(rankCustomerDTO.getDescription());
            rankCustomerDAO.setName(rankCustomerDTO.getName());
        }

        rankCustomerDAO.setActive(rankCustomerDTO.isActive());

        return rankCustomerDAO;
    }

    @Override
    public RankCustomerDTO toDTO(RankCustomerDAO rankCustomerDAO) {
        RankCustomerDTO rankCustomerDTO = new RankCustomerDTO();

        rankCustomerDTO.setId(rankCustomerDAO.getId());
        rankCustomerDTO.setPoint(rankCustomerDAO.getPoint());
        rankCustomerDTO.setDescription(rankCustomerDAO.getDescription());
        rankCustomerDTO.setName(rankCustomerDAO.getName());
        rankCustomerDTO.setActive(rankCustomerDAO.isActive());

        return rankCustomerDTO;
    }
}

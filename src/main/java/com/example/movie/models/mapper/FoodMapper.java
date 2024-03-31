package com.example.movie.models.mapper;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.FoodDTO;
import com.example.movie.models.entities.FoodDAO;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper implements BaseMapper<FoodDAO, FoodDTO, DbContext> {


    @Override
    public FoodDAO toEntity(FoodDTO foodDTO, DbContext dbContext) {

        FoodDAO foodDAO = new FoodDAO();

        if(foodDTO.getId() > 0 && dbContext.foodRepository.existsById(foodDTO.getId())){
            // update
            foodDAO = dbContext.foodRepository.findById(foodDTO.getId()).orElseThrow();

            if(foodDTO.getNameOfFood() != null && !foodDTO.getNameOfFood().isEmpty())
                foodDAO.setNameOfFood(foodDTO.getNameOfFood());
            if(foodDTO.getImage() != null && !foodDTO.getImage().isEmpty())
                foodDAO.setImage(foodDTO.getImage());
            if(foodDTO.getDescription() != null && !foodDTO.getDescription().isEmpty())
                foodDAO.setDescription(foodDTO.getDescription());

            if(foodDTO.getPrice() > 0) foodDAO.setPrice(foodDTO.getPrice());

            foodDAO.setActive(foodDTO.isActive());


        } else {
            // add
            foodDAO.setNameOfFood(foodDTO.getNameOfFood());
            foodDAO.setImage(foodDTO.getImage());
            foodDAO.setDescription(foodDTO.getDescription());
            foodDAO.setPrice(foodDTO.getPrice());
            foodDAO.setActive(foodDTO.isActive());

        }

        return foodDAO;
    }

    @Override
    public FoodDTO toDTO(FoodDAO foodDAO) {

        FoodDTO foodDTO = new FoodDTO();

        foodDTO.setId(foodDAO.getId());
        foodDTO.setNameOfFood(foodDAO.getNameOfFood());
        foodDTO.setDescription(foodDAO.getDescription());
        foodDTO.setImage(foodDAO.getImage());
        foodDTO.setPrice(foodDAO.getPrice());
        foodDTO.setActive(foodDAO.isActive());

        return foodDTO;
    }
}

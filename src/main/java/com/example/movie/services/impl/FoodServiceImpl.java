package com.example.movie.services.impl;

import com.example.movie.context.DbContext;
import com.example.movie.models.dto.FoodDTO;
import com.example.movie.models.dto.FoodFeaturedDTO;
import com.example.movie.models.entities.FoodDAO;
import com.example.movie.models.mapper.FoodMapper;
import com.example.movie.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements ICRUDService<FoodDTO, Integer> {

    @Autowired
    DbContext dbContext;

    @Autowired
    FoodMapper foodMapper;

    @Override
    public boolean add(FoodDTO foodDTO) {
        dbContext.foodRepository.save(foodMapper.toEntity(foodDTO, dbContext));
        return true;
    }

    @Override
    public boolean update(FoodDTO foodDTO) {

        if(foodDTO.getId() > 0 && dbContext.foodRepository.existsById(foodDTO.getId())){
            dbContext.foodRepository.save(foodMapper.toEntity(foodDTO, dbContext));
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if(dbContext.foodRepository.existsById(id)){
            FoodDAO foodDAO = dbContext.foodRepository.findById(id).orElseThrow();

            if(dbContext.billFoodRepository.countAllByFoodDAO(foodDAO) == 0){
                dbContext.foodRepository.deleteById(id);
                return true;
            }
        }

        return false;
    }

    public List<FoodDTO> findAll() {
        return dbContext.foodRepository.findAll()
                .stream().map(foodMapper::toDTO).toList();
    }

    public List<FoodFeaturedDTO> featuredFood7Day() {
        List<FoodFeaturedDTO> foodFeaturedDTOS = new ArrayList<>();

        List<String> foodFeatured = dbContext.foodRepository.featuredFoodIn7Day();

        for(String food : foodFeatured){
            foodFeaturedDTOS.add(new FoodFeaturedDTO(food));
        }

        return foodFeaturedDTOS;
    }
}

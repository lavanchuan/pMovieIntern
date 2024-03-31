package com.example.movie.context.repositories;

import com.example.movie.models.entities.BillFoodDAO;
import com.example.movie.models.entities.FoodDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillFoodRepository extends JpaRepository<BillFoodDAO, Integer> {

    int countAllByFoodDAO(FoodDAO foodDAO);

}

package com.example.movie.context.repositories;

import com.example.movie.models.entities.FoodDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<FoodDAO, Integer> {

    @Query(value = "select concat(food.id, '---', \n" +
            "    food.nameOfFood, '---', \n" +
            "    sum(billFood.quantity)) from food \n" +
            "    inner join billFood on billFood.foodId = food.id \n" +
            "    inner join bill on bill.id = billFood.billId \n" +
            "    where bill.createTime >= date_sub(now(), interval 7 day) \n" +
            "    group by food.id \n" +
            "    order by sum(billFood.quantity) desc", nativeQuery = true)
    List<String> featuredFoodIn7Day();
}

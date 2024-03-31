package com.example.movie.context.repositories;

import com.example.movie.models.entities.PromotionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionDAO, Integer> {

    @Query(value = "select count(promotion.id) from promotion \n" +
            "where rankCustomerId = :rankCustomerId", nativeQuery = true)
    int countByRankCustomerId(int rankCustomerId);

    @Query(value = "select distinct promotion.* from promotion \n" +
            "inner join rankCustomer on rankCustomer.id = promotion.rankCustomerId \n" +
            "inner join user on user.rankCustomerId = rankCustomer.id \n" +
            "where username like :username", nativeQuery = true)
    List<PromotionDAO> findByUsername(String username);

    @Query(value = "select distinct promotion.id from promotion \n" +
            "inner join rankCustomer on rankCustomer.id = promotion.rankCustomerId \n" +
            "inner join user on user.rankCustomerId = rankCustomer.id \n" +
            "where username like :username", nativeQuery = true)
    List<Integer> findIdAllByUsername(String username);
}


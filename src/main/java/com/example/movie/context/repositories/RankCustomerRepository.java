package com.example.movie.context.repositories;

import com.example.movie.models.entities.RankCustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankCustomerRepository extends JpaRepository<RankCustomerDAO, Integer> {
}

package com.example.movie.context.repositories;

import com.example.movie.models.entities.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Integer> {

    UserDAO findByUsername(String username);

    UserDAO findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query(value = "select count(user.id) from user \n" +
            "where rankCustomerId = :rankCustomerId", nativeQuery = true)
    int countByRankCustomerId(int rankCustomerId);
}

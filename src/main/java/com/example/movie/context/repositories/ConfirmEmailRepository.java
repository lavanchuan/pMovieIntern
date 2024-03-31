package com.example.movie.context.repositories;

import com.example.movie.models.entities.ConfirmEmailDAO;
import com.example.movie.models.entities.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmailDAO, Integer> {

    ConfirmEmailDAO findByUserDAO(UserDAO userDAO);

    @Query(value = "select count(confirmEmail.id) from confirmEmail \n" +
            "where confirmEmail.userId = :userId", nativeQuery = true)
    int countByUserId(@Param(value = "userId") int userId);
}

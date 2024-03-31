package com.example.movie.context.repositories;

import com.example.movie.models.entities.RefreshTokenDAO;
import com.example.movie.models.entities.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenDAO, Integer> {
    RefreshTokenDAO findByUserDAO(UserDAO user);

    List<RefreshTokenDAO> findRefreshTokenDAOSByUserDAO(UserDAO userDAO);

    boolean existsByToken(String token);

    @Query(value = "select count(refreshToken.id) from refreshToken \n" +
            "where refreshToken.userId = :userId", nativeQuery = true)
    int countByUserId(@Param(value = "userId") int userId);
}

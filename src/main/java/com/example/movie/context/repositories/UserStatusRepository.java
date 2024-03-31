package com.example.movie.context.repositories;

import com.example.movie.models.UserStatus;
import com.example.movie.models.entities.UserStatusDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusDAO, Integer> {
    UserStatusDAO findByName(UserStatus status);
}

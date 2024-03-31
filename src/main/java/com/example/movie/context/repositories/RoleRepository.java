package com.example.movie.context.repositories;

import com.example.movie.models.Role;
import com.example.movie.models.entities.RoleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDAO, Integer> {
    RoleDAO findByRoleName(Role role);

    boolean existsByRoleName(Role role);
}

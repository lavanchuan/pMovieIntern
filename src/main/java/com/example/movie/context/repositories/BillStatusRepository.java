package com.example.movie.context.repositories;

import com.example.movie.models.BillStatus;
import com.example.movie.models.entities.BillStatusDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillStatusRepository extends JpaRepository<BillStatusDAO, Integer> {
    BillStatusDAO findByName(BillStatus billStatus);
}

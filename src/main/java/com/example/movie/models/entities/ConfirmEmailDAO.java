package com.example.movie.models.entities;

import com.example.movie.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ConfirmEmail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmEmailDAO extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserDAO userDAO;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime requiredTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime expiredTime;
    private String confirmCode;
    private boolean isConfirm;
}
